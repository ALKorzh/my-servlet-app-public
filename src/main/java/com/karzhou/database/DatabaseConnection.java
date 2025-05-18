package com.karzhou.database;

import com.karzhou.config.Config;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final ConnectionPool pool;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            pool = new SimpleConnectionPool(
                    10,
                    Config.getProperty("db.url"),
                    Config.getProperty("db.username"),
                    Config.getProperty("db.password")
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize connection pool", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return pool.getConnection();
    }
}
