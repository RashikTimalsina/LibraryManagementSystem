package main.java.org.rashiktimalsina.db.connection;

import main.java.org.rashiktimalsina.db.DatabaseConfig;

import java.sql.*;
/**
 * @author RashikTimalsina
 * @created 10/05/2025
 */
public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    DatabaseConfig.URL,
                    DatabaseConfig.USER,
                    DatabaseConfig.PASSWORD
            );
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

}