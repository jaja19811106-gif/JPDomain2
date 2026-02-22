package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dto.ContactDto;

/**
 * コンタクトDAO
 */
public class ContactDao extends BaseDao {

    // ResultSet → DTO のマッピング
    private ContactDto map(ResultSet rs) throws SQLException {
        ContactDto dto = new ContactDto();
        dto.setId(rs.getInt("id"));
        dto.setContactId(rs.getString("contact_id"));
        dto.setContactType(rs.getString("contact_type"));
        dto.setLang(rs.getString("lang"));
        dto.setName(rs.getString("name"));
        dto.setOrganization(rs.getString("organization"));
        dto.setPostalCode(rs.getString("postal_code"));
        dto.setSp(rs.getString("sp"));
        dto.setCity(rs.getString("city"));
        dto.setStreet(rs.getString("street"));
        dto.setCc(rs.getString("cc"));
        dto.setVoice(rs.getString("voice"));
        dto.setFax(rs.getString("fax"));
        dto.setEmail(rs.getString("email"));
        return dto;
    }

    // INSERT（採番したcontact_idを返す）
    public String insert(ContactDto dto) throws SQLException {
        String contactId = generateContactId();
        dto.setContactId(contactId);

        String sql = """
                INSERT INTO contact (
                    contact_id, contact_type, lang, name, organization,
                    postal_code, sp, city, street, cc, voice, fax, email
                ) VALUES (
                    ?, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?, ?, ?, ?
                )
                """;

        executeUpdate(sql, ps -> {
            ps.setString(1,  dto.getContactId());
            ps.setString(2,  dto.getContactType());
            ps.setString(3,  dto.getLang());
            ps.setString(4,  dto.getName());
            ps.setString(5,  dto.getOrganization());
            ps.setString(6,  dto.getPostalCode());
            ps.setString(7,  dto.getSp());
            ps.setString(8,  dto.getCity());
            ps.setString(9,  dto.getStreet());
            ps.setString(10, dto.getCc());
            ps.setString(11, dto.getVoice());
            ps.setString(12, dto.getFax());
            ps.setString(13, dto.getEmail());
        });

        return contactId;
    }

    // IDで検索（単一）
    public ContactDto findById(int id) throws SQLException {
        String sql = """
                SELECT id, contact_id, contact_type, lang, name, organization,
                       postal_code, sp, city, street, cc, voice, fax, email
                FROM contact
                WHERE id = ?
                """;
        List<ContactDto> list = selectList(sql,
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

    // contact_idで検索（単一）
    public ContactDto findByContactId(String contactId) throws SQLException {
        String sql = """
                SELECT id, contact_id, contact_type, lang, name, organization,
                       postal_code, sp, city, street, cc, voice, fax, email
                FROM contact
                WHERE contact_id = ?
                """;
        List<ContactDto> list = selectList(sql,
                ps -> ps.setString(1, contactId),
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

    // 氏名で部分一致検索
    public List<ContactDto> findByName(String name) throws SQLException {
        String sql = """
                SELECT id, contact_id, contact_type, lang, name, organization,
                       postal_code, sp, city, street, cc, voice, fax, email
                FROM contact
                WHERE name LIKE ?
                """;
        String escaped = escapeLikePattern(name);
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

    // UPDATE（更新件数を返す）
    public int update(ContactDto dto) throws SQLException {
        String sql = """
                UPDATE contact
                SET contact_type=?, lang=?, name=?, organization=?,
                    postal_code=?, sp=?, city=?, street=?, cc=?, voice=?, fax=?, email=?
                WHERE id=?
                """;
        return executeUpdate(sql, ps -> {
            ps.setString(1,  dto.getContactType());
            ps.setString(2,  dto.getLang());
            ps.setString(3,  dto.getName());
            ps.setString(4,  dto.getOrganization());
            ps.setString(5,  dto.getPostalCode());
            ps.setString(6,  dto.getSp());
            ps.setString(7,  dto.getCity());
            ps.setString(8,  dto.getStreet());
            ps.setString(9,  dto.getCc());
            ps.setString(10, dto.getVoice());
            ps.setString(11, dto.getFax());
            ps.setString(12, dto.getEmail());
            ps.setInt(13,    dto.getId());
        });
    }

    // DELETE（削除件数を返す）
    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM contact WHERE id = ?";
        return executeUpdate(sql, ps -> ps.setInt(1, id));
    }

    // 全件取得
    public List<ContactDto> findAll() throws SQLException {
        String sql = """
                SELECT id, contact_id, contact_type, lang, name, organization,
                       postal_code, sp, city, street, cc, voice, fax, email
                FROM contact
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

    // contact_id採番（C- + 8桁ゼロ埋め連番）
    private String generateContactId() throws SQLException {
        String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(contact_id FROM 3) AS INTEGER)), 0) + 1 FROM contact";
        List<Integer> list = selectList(sql,
                ps -> {},
                rs -> {
                    try {
                        return rs.getInt(1);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        int next = list.isEmpty() ? 1 : list.get(0);
        return String.format("C-%08d", next);
    }

    // LIKE検索用エスケープ
    private String escapeLikePattern(String input) {
        if (input == null) return null;
        return input.replace("\\", "\\\\")
                    .replace("%", "\\%")
                    .replace("_", "\\_");
    }
}