package dao;

import java.sql.SQLException;
import java.util.List;

public class OrganizationDomainDao extends BaseDao {

    // 1組織1ドメインの制約チェック用 SQL
    private static final String SQL_COUNT_BY_CORP_AND_ATTR =
            "SELECT COUNT(*) AS cnt " +
            "FROM organization_domain " +
            "WHERE corporate_number = ? " +
            "AND attribute_type = ? " +
            "AND relaxation_flag = FALSE";

    /**
     * 指定された法人番号＋属性型JPドメインが既に存在するかチェックする
     *
     * @param corporateNumber 法人番号
     * @param attributeType   属性型（co.jp / or.jp / ac.jp など）
     * @return true = 既に存在する（登録不可）
     */
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
                }
        );

        return !result.isEmpty() && result.get(0) > 0;
    }
}