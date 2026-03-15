package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.OrganizationDomain;

public class OrganizationDomainDao extends BaseDao {

    // ---------------------------------------------------------
    // マッピング共通メソッド
    // ---------------------------------------------------------
    private OrganizationDomain map(ResultSet rs) throws SQLException {
        OrganizationDomain d = new OrganizationDomain();
        d.setId(rs.getInt("id"));
        d.setCorporateNumber(rs.getString("corporate_number"));
        d.setDomainName(rs.getString("domain_name"));
        d.setAttributeType(rs.getString("attribute_type"));
        d.setAuthCode(rs.getString("auth_code"));
        d.setAuthCodeExpiresAt(rs.getTimestamp("auth_code_expires_at"));
        d.setStatus(rs.getInt("status"));
        d.setIp1From(rs.getString("ip1_from")); d.setIp1To(rs.getString("ip1_to"));
        d.setIp2From(rs.getString("ip2_from")); d.setIp2To(rs.getString("ip2_to"));
        d.setIp3From(rs.getString("ip3_from")); d.setIp3To(rs.getString("ip3_to"));
        d.setIp4From(rs.getString("ip4_from")); d.setIp4To(rs.getString("ip4_to"));
        d.setIp5From(rs.getString("ip5_from")); d.setIp5To(rs.getString("ip5_to"));
        d.setHost1(rs.getString("host1"));
        d.setHost2(rs.getString("host2"));
        d.setHost3(rs.getString("host3"));
        d.setHost4(rs.getString("host4"));
        d.setHost5(rs.getString("host5"));
        return d;
    }

    private static final String SELECT_COLS =
        "id, corporate_number, domain_name, attribute_type, auth_code, auth_code_expires_at, status, " +
        "ip1_from, ip1_to, ip2_from, ip2_to, ip3_from, ip3_to, ip4_from, ip4_to, ip5_from, ip5_to, " +
        "host1, host2, host3, host4, host5";

    // ---------------------------------------------------------
    // 1組織1ドメインの制約チェック
    // ---------------------------------------------------------
    private static final String SQL_COUNT_BY_CORP_AND_ATTR =
        "SELECT COUNT(*) AS cnt FROM organization_domain " +
        "WHERE corporate_number = ? AND attribute_type = ? AND relaxation_flag = FALSE";

    public boolean existsActiveDomainForOrg(String corporateNumber, String attributeType) throws SQLException {
        List<Integer> result = selectList(
            SQL_COUNT_BY_CORP_AND_ATTR,
            ps -> {
                ps.setString(1, corporateNumber);
                ps.setString(2, attributeType);
            },
            rs -> {
                try { return rs.getInt("cnt"); }
                catch (SQLException e) { throw new RuntimeException(e); }
            });
        return !result.isEmpty() && result.get(0) > 0;
    }

    // ---------------------------------------------------------
    // INSERT（新規登録）
    // ---------------------------------------------------------
    private static final String SQL_INSERT =
        "INSERT INTO organization_domain " +
        "(corporate_number, domain_name, attribute_type, auth_code, auth_code_expires_at, status, " +
        " ip1_from, ip1_to, ip2_from, ip2_to, ip3_from, ip3_to, ip4_from, ip4_to, ip5_from, ip5_to, " +
        " host1, host2, host3, host4, host5) " +
        "VALUES (?, ?, ?, generate_auth_code(), " +
        "        (CURRENT_DATE + INTERVAL '35 days')::DATE + TIME '23:59:59', 1, " +
        "        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
        "RETURNING id";

    public int insert(OrganizationDomain domain) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
            ps.setString(1, domain.getCorporateNumber());
            ps.setString(2, domain.getDomainName());
            ps.setString(3, domain.getAttributeType());
            ps.setString(4,  nullIfBlank(domain.getIp1From())); ps.setString(5,  nullIfBlank(domain.getIp1To()));
            ps.setString(6,  nullIfBlank(domain.getIp2From())); ps.setString(7,  nullIfBlank(domain.getIp2To()));
            ps.setString(8,  nullIfBlank(domain.getIp3From())); ps.setString(9,  nullIfBlank(domain.getIp3To()));
            ps.setString(10, nullIfBlank(domain.getIp4From())); ps.setString(11, nullIfBlank(domain.getIp4To()));
            ps.setString(12, nullIfBlank(domain.getIp5From())); ps.setString(13, nullIfBlank(domain.getIp5To()));
            ps.setString(14, domain.getHost1());
            ps.setString(15, domain.getHost2());
            ps.setString(16, domain.getHost3());
            ps.setString(17, domain.getHost4());
            ps.setString(18, domain.getHost5());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        }
        throw new SQLException("ID が取得できませんでした");
    }

    // ---------------------------------------------------------
    // SELECT 全件取得
    // ---------------------------------------------------------
    public List<OrganizationDomain> findAll() {
        List<OrganizationDomain> list = new ArrayList<>();
        String sql = "SELECT " + SELECT_COLS + " FROM organization_domain ORDER BY id";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // ---------------------------------------------------------
    // SELECT ID指定 FOR UPDATE
    // ---------------------------------------------------------
    public OrganizationDomain findByIdForUpdate(int id) throws SQLException {
        String sql = "SELECT " + SELECT_COLS + " FROM organization_domain WHERE id = ? FOR UPDATE";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    // ---------------------------------------------------------
    // SELECT ID指定（参照用）
    // ---------------------------------------------------------
    public OrganizationDomain findById(int id) {
        String sql = "SELECT " + SELECT_COLS + " FROM organization_domain WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // ---------------------------------------------------------
    // SELECT ドメイン名で検索（完全一致）
    // ---------------------------------------------------------
    public OrganizationDomain findByDomainName(String domainName) throws SQLException {
        String sql = "SELECT " + SELECT_COLS + " FROM organization_domain WHERE domain_name = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, domainName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    // ---------------------------------------------------------
    // UPDATE
    // ---------------------------------------------------------
    public void update(OrganizationDomain domain) throws SQLException {
        String sql =
            "UPDATE organization_domain " +
            "SET corporate_number=?, domain_name=?, attribute_type=?, " +
            "    auth_code=?, auth_code_expires_at=?, status=?, " +
            "    ip1_from=?, ip1_to=?, ip2_from=?, ip2_to=?, ip3_from=?, ip3_to=?, " +
            "    ip4_from=?, ip4_to=?, ip5_from=?, ip5_to=?, " +
            "    host1=?, host2=?, host3=?, host4=?, host5=? " +
            "WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, domain.getCorporateNumber());
            ps.setString(2, domain.getDomainName());
            ps.setString(3, domain.getAttributeType());
            ps.setString(4, domain.getAuthCode());
            ps.setTimestamp(5, domain.getAuthCodeExpiresAt());
            ps.setInt(6, domain.getStatus());
            ps.setString(7,  nullIfBlank(domain.getIp1From())); ps.setString(8,  nullIfBlank(domain.getIp1To()));
            ps.setString(9,  nullIfBlank(domain.getIp2From())); ps.setString(10, nullIfBlank(domain.getIp2To()));
            ps.setString(11, nullIfBlank(domain.getIp3From())); ps.setString(12, nullIfBlank(domain.getIp3To()));
            ps.setString(13, nullIfBlank(domain.getIp4From())); ps.setString(14, nullIfBlank(domain.getIp4To()));
            ps.setString(15, nullIfBlank(domain.getIp5From())); ps.setString(16, nullIfBlank(domain.getIp5To()));
            ps.setString(17, domain.getHost1());
            ps.setString(18, domain.getHost2());
            ps.setString(19, domain.getHost3());
            ps.setString(20, domain.getHost4());
            ps.setString(21, domain.getHost5());
            ps.setInt(22, domain.getId());
            ps.executeUpdate();
        }
    }

    // ---------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM organization_domain WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // ---------------------------------------------------------
    // 有効期限のみ更新
    // ---------------------------------------------------------
    public void updateAuthCodeExpiresAt(int id) throws SQLException {
        String sql =
            "UPDATE organization_domain " +
            "SET auth_code_expires_at = (CURRENT_DATE + INTERVAL '35 days')::DATE + TIME '23:59:59' " +
            "WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private String nullIfBlank(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
}
