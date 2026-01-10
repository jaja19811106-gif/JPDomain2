package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dto.DomainDto;

public class DomainDao extends BaseDao {

	private DomainDto map(ResultSet rs) {
	    try {
	        DomainDto dto = new DomainDto();
	        dto.setId(rs.getInt("id"));
	        dto.setDomainName(rs.getString("domain_name"));
	        dto.setRegistrant(rs.getString("registrant"));
	        dto.setNs1(rs.getString("ns1"));
	        dto.setNs2(rs.getString("ns2"));
	        dto.setPeriod(rs.getInt("period"));
	        return dto;
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

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

    public List<DomainDto> findByDomainName(String domainName) throws SQLException {
        String sql = "SELECT * FROM domain WHERE domain_name LIKE ?";

        return selectList(sql,
                ps -> ps.setString(1, "%" + domainName + "%"),
                rs -> map(rs)
        );
    }

    public void update(DomainDto dto) throws SQLException {
        String sql = """
            UPDATE domain
            SET domain_name=?, registrant=?, ns1=?, ns2=?, period=?
            WHERE id=?
            """;

        executeUpdate(sql, ps -> {
            ps.setString(1, dto.getDomainName());
            ps.setString(2, dto.getRegistrant());
            ps.setString(3, dto.getNs1());
            ps.setString(4, dto.getNs2());
            ps.setInt(5, dto.getPeriod());
            ps.setInt(6, dto.getId());
        });
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM domain WHERE id = ?";

        executeUpdate(sql, ps -> ps.setInt(1, id));
    }
}