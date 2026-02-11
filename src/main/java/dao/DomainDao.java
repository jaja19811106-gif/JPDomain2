package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dto.DomainDto;

public class DomainDao extends BaseDao {
    
    // ResultSet → DTO のマッピング
    private DomainDto map(ResultSet rs) throws SQLException {
        DomainDto dto = new DomainDto();
        dto.setId(rs.getInt("id"));
        dto.setDomainName(rs.getString("domain_name"));
        dto.setRegistrant(rs.getString("registrant"));
        dto.setNs1(rs.getString("ns1"));
        dto.setNs2(rs.getString("ns2"));
        dto.setPeriod(rs.getInt("period"));
        return dto;
    }
    
    // INSERT
    public void insert(DomainDto dto) throws SQLException {
        String sql = """
            INSERT INTO domain (domain_name, registrant, ns1, ns2, period)
            VALUES (?, ?, ?, ?, ?)
            """;
        executeUpdate(sql, ps -> {
            ps.setString(1, dto.getDomainName());
            ps.setString(2, dto.getRegistrant());
            ps.setString(3, dto.getNs1());
            ps.setString(4, dto.getNs2());
            ps.setInt(5, dto.getPeriod());
        });
    }
    
    // ID で検索（単一）
    public DomainDto findById(int id) throws SQLException {
        String sql = """
            SELECT id, domain_name, registrant, ns1, ns2, period
            FROM domain
            WHERE id = ?
            """;
        List<DomainDto> list = selectList(sql,
                ps -> ps.setInt(1, id),
                rs -> {
                    try {
                        return map(rs);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return list.isEmpty() ? null : list.get(0);
    }
    
    // ドメイン名で部分一致検索（LIKE検索）
    public List<DomainDto> findByDomainName(String domainName) throws SQLException {
        String sql = """
            SELECT id, domain_name, registrant, ns1, ns2, period
            FROM domain
            WHERE domain_name LIKE ?
            """;
        
        String escaped = escapeLikePattern(domainName);
        
        return selectList(sql,
                ps -> ps.setString(1, "%" + escaped + "%"),
                rs -> {
                    try {
                        return map(rs);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
    
    // LIKE検索用のエスケープ処理
    private String escapeLikePattern(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("\\", "\\\\")
                    .replace("%", "\\%")
                    .replace("_", "\\_");
    }
    
    // UPDATE（更新件数を返す）
    public int update(DomainDto dto) throws SQLException {
        String sql = """
            UPDATE domain
            SET domain_name=?, registrant=?, ns1=?, ns2=?, period=?
            WHERE id=?
            """;
        
        return executeUpdate(sql, ps -> {
            ps.setString(1, dto.getDomainName());
            ps.setString(2, dto.getRegistrant());
            ps.setString(3, dto.getNs1());
            ps.setString(4, dto.getNs2());
            ps.setInt(5, dto.getPeriod());
            ps.setInt(6, dto.getId());
        });
    }
    
    // DELETE（削除件数を返す）
    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM domain WHERE id = ?";
        return executeUpdate(sql, ps -> ps.setInt(1, id));
    }
    
    // 全件取得（管理画面用など）
    public List<DomainDto> findAll() throws SQLException {
        String sql = """
            SELECT id, domain_name, registrant, ns1, ns2, period
            FROM domain
            ORDER BY id
            """;
        return selectList(sql,
                ps -> {},
                rs -> {
                    try {
                        return map(rs);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}