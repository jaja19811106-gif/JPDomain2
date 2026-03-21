package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * テスト用DBManager（JDBC直接接続）
 * src/test/java/dao/ に配置することでテスト時のみ使用される
 */
public class DBManager {

    private static final String URL      = "jdbc:postgresql://localhost:5432/testdb";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "postgres";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQLドライバが見つかりません", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
