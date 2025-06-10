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
        } catch (ClassNotFoundException ce) {
            throw new SQLException("MySQL JDBC Driver not found", ce);
        }
        catch(SQLException se) {
            throw new SQLException("Failed to connect to the database", se);
        } catch (Exception e) {
            throw new SQLException("An unexpected error occurred while connecting to the database", e);
        }
    }

}