package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.BaseDao;

public class AuthCodeUtil {

    public static String generateAuthCode() throws Exception {
        String sql = "SELECT generate_auth_code() AS code";

        try (Connection con = BaseDao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getString("code");
            }
        }
        return null;
    }
}