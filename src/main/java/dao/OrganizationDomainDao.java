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
	// 1組織1ドメインの制約チェック
	// ---------------------------------------------------------
	private static final String SQL_COUNT_BY_CORP_AND_ATTR = "SELECT COUNT(*) AS cnt " +
			"FROM organization_domain " +
			"WHERE corporate_number = ? " +
			"AND attribute_type = ? " +
			"AND relaxation_flag = FALSE";

	public boolean existsActiveDomainForOrg(String corporateNumber, String attributeType) throws SQLException {

		List<Integer> result = selectList(
				SQL_COUNT_BY_CORP_AND_ATTR,
				ps -> {
					ps.setString(1, corporateNumber);
					ps.setString(2, attributeType);
				},
				rs -> {
					try {
						return rs.getInt("cnt");
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				});

		return !result.isEmpty() && result.get(0) > 0;
	}

	// ---------------------------------------------------------
	// INSERT（新規登録）
	// ---------------------------------------------------------
	private static final String SQL_INSERT = "INSERT INTO organization_domain " +
			"(corporate_number, domain_name, attribute_type, auth_code, auth_code_expires_at, status) " +
			"VALUES (?, ?, ?, generate_auth_code(), " +
			"        (CURRENT_DATE + INTERVAL '35 days')::DATE + TIME '23:59:59', " +
			"        1) " +
			"RETURNING id";

	public int insert(OrganizationDomain domain) throws SQLException {

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

			ps.setString(1, domain.getCorporateNumber());
			ps.setString(2, domain.getDomainName());
			ps.setString(3, domain.getAttributeType());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
			}
		}
		throw new SQLException("ID が取得できませんでした");
	}

	// ---------------------------------------------------------
	// SELECT 全件取得（一覧画面用）
	// ---------------------------------------------------------
	public List<OrganizationDomain> findAll() {

		List<OrganizationDomain> list = new ArrayList<>();

		String sql = "SELECT id, corporate_number, domain_name, attribute_type, auth_code, auth_code_expires_at, status "
				+
				"FROM organization_domain ORDER BY id";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				OrganizationDomain d = new OrganizationDomain();
				d.setId(rs.getInt("id"));
				d.setCorporateNumber(rs.getString("corporate_number"));
				d.setDomainName(rs.getString("domain_name"));
				d.setAttributeType(rs.getString("attribute_type"));
				d.setAuthCode(rs.getString("auth_code"));
				d.setAuthCodeExpiresAt(rs.getTimestamp("auth_code_expires_at"));
				d.setStatus(rs.getInt("status"));
				list.add(d);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return list;
	}

	// ---------------------------------------------------------
	// SELECT ID指定 FOR UPDATE（更新画面用・排他制御）
	// ---------------------------------------------------------
	public OrganizationDomain findByIdForUpdate(int id) throws SQLException {

		String sql = "SELECT id, corporate_number, domain_name, attribute_type, auth_code, auth_code_expires_at, status "
				+ "FROM organization_domain "
				+ "WHERE id = ? "
				+ "FOR UPDATE";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					OrganizationDomain d = new OrganizationDomain();
					d.setId(rs.getInt("id"));
					d.setCorporateNumber(rs.getString("corporate_number"));
					d.setDomainName(rs.getString("domain_name"));
					d.setAttributeType(rs.getString("attribute_type"));
					d.setAuthCode(rs.getString("auth_code"));
					d.setAuthCodeExpiresAt(rs.getTimestamp("auth_code_expires_at"));
					d.setStatus(rs.getInt("status"));
					return d;
				}
			}
		}

		return null;
	}

	// ---------------------------------------------------------
	// SELECT ID指定（参照用）
	// ---------------------------------------------------------
	public OrganizationDomain findById(int id) {

		String sql = "SELECT id, corporate_number, domain_name, attribute_type, auth_code, auth_code_expires_at, status "
				+
				"FROM organization_domain WHERE id = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					OrganizationDomain d = new OrganizationDomain();
					d.setId(rs.getInt("id"));
					d.setCorporateNumber(rs.getString("corporate_number"));
					d.setDomainName(rs.getString("domain_name"));
					d.setAttributeType(rs.getString("attribute_type"));
					d.setAuthCode(rs.getString("auth_code"));
					d.setAuthCodeExpiresAt(rs.getTimestamp("auth_code_expires_at"));
					d.setStatus(rs.getInt("status"));
					return d;
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	// ---------------------------------------------------------
	// UPDATE（更新処理）
	// ---------------------------------------------------------
	public void update(OrganizationDomain domain) throws SQLException {

		String sql = "UPDATE organization_domain "
				+ "SET corporate_number = ?, "
				+ "    domain_name = ?, "
				+ "    attribute_type = ?, "
				+ "    auth_code = ?, "
				+ "    auth_code_expires_at = ?, "
				+ "    status = ? "
				+ "WHERE id = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, domain.getCorporateNumber());
			ps.setString(2, domain.getDomainName());
			ps.setString(3, domain.getAttributeType());
			ps.setString(4, domain.getAuthCode());
			ps.setTimestamp(5, domain.getAuthCodeExpiresAt());
			ps.setInt(6, domain.getStatus());
			ps.setInt(7, domain.getId());

			ps.executeUpdate();
		}
	}

	// ---------------------------------------------------------
	// DELETE（削除処理）
	// ---------------------------------------------------------
	public void delete(int id) throws SQLException {

		String sql = "DELETE FROM organization_domain WHERE id = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);
			ps.executeUpdate();
		}
	}

	// OrganizationDomainDao.java に追加

	// ---------------------------------------------------------
	// ドメイン名で検索（完全一致）
	// ---------------------------------------------------------
	public OrganizationDomain findByDomainName(String domainName) throws SQLException {

		String sql = "SELECT id, corporate_number, domain_name, attribute_type, auth_code, auth_code_expires_at, status "
				+
				"FROM organization_domain " +
				"WHERE domain_name = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, domainName);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					OrganizationDomain d = new OrganizationDomain();
					d.setId(rs.getInt("id"));
					d.setCorporateNumber(rs.getString("corporate_number"));
					d.setDomainName(rs.getString("domain_name"));
					d.setAttributeType(rs.getString("attribute_type"));
					d.setAuthCode(rs.getString("auth_code"));
					d.setAuthCodeExpiresAt(rs.getTimestamp("auth_code_expires_at"));
					d.setStatus(rs.getInt("status"));
					return d;
				}
			}
		}

		return null;
	}

	// ---------------------------------------------------------
	// 有効期限のみ更新（35日後の23:59:59に設定）
	// ---------------------------------------------------------
	public void updateAuthCodeExpiresAt(int id) throws SQLException {

	    String sql = "UPDATE organization_domain " +
	                 "SET auth_code_expires_at = (CURRENT_DATE + INTERVAL '35 days')::DATE + TIME '23:59:59' " +
	                 "WHERE id = ?";

	    try (Connection con = getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, id);
	        ps.executeUpdate();
	    }
	}

}