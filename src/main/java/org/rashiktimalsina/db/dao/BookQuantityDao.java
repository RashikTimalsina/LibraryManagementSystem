package main.java.org.rashiktimalsina.db.dao;
import main.java.org.rashiktimalsina.db.connection.DatabaseConnection;
import main.java.org.rashiktimalsina.entities.Book;

import java.sql.*;


/**
 * @author RashikTimalsina
 * @created 11/05/2025
 */
public class BookQuantityDao {
    public void addBook(Book book, int quantity) throws SQLException {
        String sql = "INSERT INTO book_quantities (book_id, quantity) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getId());
            stmt.setInt(2, quantity);
            stmt.executeUpdate();
        }
    }

    public boolean isBookAvailable(Book book) throws SQLException {
        String sql = "SELECT quantity FROM book_quantities WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantity") > 0;
                }
            }
        }
        return false;
    }

    public void decreaseQuantity(Book book) throws SQLException {
        String sql = "UPDATE book_quantities SET quantity = quantity - 1 WHERE book_id = ? AND quantity > 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getId());
            stmt.executeUpdate();
        }
    }

    public void increaseQuantity(Book book) throws SQLException {
        String sql = "UPDATE book_quantities SET quantity = quantity + 1 WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getId());
            stmt.executeUpdate();
        }
    }

    public int getQuantity(Book book) throws SQLException {
        String sql = "SELECT quantity FROM book_quantities WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantity");
                }
            }
        }
        return 0;
    }
}
