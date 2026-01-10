package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class BaseDao {

    protected Connection getConnection() throws SQLException {
        return DBManager.getConnection();
    }

    // SELECT 共通処理
    protected <T> List<T> selectList(
            String sql,
            PreparedStatementSetter setter,
            Function<ResultSet, T> mapper
    ) throws SQLException {

        List<T> list = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (setter != null) {
                setter.set(ps);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapper.apply(rs));
                }
            }
        }
        return list;
    }

    // INSERT / UPDATE / DELETE 共通処理
    protected int executeUpdate(
            String sql,
            PreparedStatementSetter setter
    ) throws SQLException {

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (setter != null) {
                setter.set(ps);
            }

            return ps.executeUpdate();
        }
    }

    // PreparedStatement に値をセットするための関数型インターフェース
    @FunctionalInterface
    protected interface PreparedStatementSetter {
        void set(PreparedStatement ps) throws SQLException;
    }
}