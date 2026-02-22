package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dto.HostDto;

/**
 * ホストDAO
 */
public class HostDao extends BaseDao {

    // ResultSet → DTO のマッピング
    private HostDto map(ResultSet rs) throws SQLException {
        HostDto dto = new HostDto();
        dto.setId(rs.getInt("id"));
        dto.setHostName(rs.getString("host_name"));
        dto.setIpv4(rs.getString("ipv4"));
        dto.setIpv6(rs.getString("ipv6"));
        return dto;
    }

    // INSERT
    public void insert(HostDto dto) throws SQLException {
        String sql = """
                INSERT INTO host (host_name, ipv4, ipv6)
                VALUES (?, ?, ?)
                """;
        executeUpdate(sql, ps -> {
            ps.setString(1, dto.getHostName());
            ps.setString(2, dto.getIpv4());
            ps.setString(3, dto.getIpv6());
        });
    }

    // IDで検索（単一）
    public HostDto findById(int id) throws SQLException {
        String sql = """
                SELECT id, host_name, ipv4, ipv6
                FROM host
                WHERE id = ?
                """;
        List<HostDto> list = selectList(sql,
                ps -> ps.setInt(1, id),
                rs -> {
                    try { return map(rs); }
                    catch (SQLException e) { throw new RuntimeException(e); }
                }
        );
        return list.isEmpty() ? null : list.get(0);
    }

    // ホスト名で検索（単一）
    public HostDto findByHostName(String hostName) throws SQLException {
        String sql = """
                SELECT id, host_name, ipv4, ipv6
                FROM host
                WHERE host_name = ?
                """;
        List<HostDto> list = selectList(sql,
                ps -> ps.setString(1, hostName),
                rs -> {
                    try { return map(rs); }
                    catch (SQLException e) { throw new RuntimeException(e); }
                }
        );
        return list.isEmpty() ? null : list.get(0);
    }

    // ホスト名で部分一致検索
    public List<HostDto> findByHostNameLike(String hostName) throws SQLException {
        String sql = """
                SELECT id, host_name, ipv4, ipv6
                FROM host
                WHERE host_name LIKE ?
                """;
        String escaped = escapeLikePattern(hostName);
        return selectList(sql,
                ps -> ps.setString(1, "%" + escaped + "%"),
                rs -> {
                    try { return map(rs); }
                    catch (SQLException e) { throw new RuntimeException(e); }
                }
        );
    }

    // UPDATE（更新件数を返す）
    public int update(HostDto dto) throws SQLException {
        String sql = """
                UPDATE host
                SET host_name=?, ipv4=?, ipv6=?
                WHERE id=?
                """;
        return executeUpdate(sql, ps -> {
            ps.setString(1, dto.getHostName());
            ps.setString(2, dto.getIpv4());
            ps.setString(3, dto.getIpv6());
            ps.setInt(4,    dto.getId());
        });
    }

    // DELETE（削除件数を返す）
    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM host WHERE id = ?";
        return executeUpdate(sql, ps -> ps.setInt(1, id));
    }

    // 全件取得
    public List<HostDto> findAll() throws SQLException {
        String sql = """
                SELECT id, host_name, ipv4, ipv6
                FROM host
                ORDER BY id
                """;
        return selectList(sql,
                ps -> {},
                rs -> {
                    try { return map(rs); }
                    catch (SQLException e) { throw new RuntimeException(e); }
                }
        );
    }

    // LIKE検索用エスケープ
    private String escapeLikePattern(String input) {
        if (input == null) return null;
        return input.replace("\\", "\\\\")
                    .replace("%", "\\%")
                    .replace("_", "\\_");
    }
}