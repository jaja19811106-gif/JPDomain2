package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.ProviderAccount;
import util.PasswordUtil;

public class ProviderAccountDao {

    public ProviderAccount findByLogin(String providerNo, String loginId, String password) {

        String sql = "SELECT provider_no, login_id, name "
                   + "FROM provider_accounts "
                   + "WHERE provider_no = ? AND login_id = ? AND password_hash = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // パスワードはハッシュ化して比較する
            String hashed = PasswordUtil.hash(password);

            ps.setString(1, providerNo);
            ps.setString(2, loginId);
            ps.setString(3, hashed);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ProviderAccount(
                    rs.getString("provider_no"),
                    rs.getString("login_id"),
                    rs.getString("name")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}