package main.java.org.rashiktimalsina.db.dao;

import main.java.org.rashiktimalsina.db.connection.DatabaseConnection;
import main.java.org.rashiktimalsina.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 11/05/2025
 */
public class BookDao {
    public int addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author) VALUES ( ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new SQLException("Failed to get generated book ID");
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author")
                ));
            }
        }
        return books;

    }

    public Book findBookById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author")
                    );
                }
            }
        }
        return null;

    }

    public List<Book> findBooksByTitle(String title) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + title + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author")
                    ));
                }
            }
        }
        return books;
    }

    public List<Book> findBooksByAuthor(String author) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE LOWER(author) LIKE LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + author + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author")
                    ));
                }
            }
        }
        return books;
    }


    public boolean deleteBook(int id) throws SQLException {
        String deleteQuantitiesSql = "DELETE FROM book_quantities WHERE book_id = ?";
        String deleteBookSql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Begin transaction
            try (
                    PreparedStatement deleteQuantitiesStmt = conn.prepareStatement(deleteQuantitiesSql);
                    PreparedStatement deleteBookStmt = conn.prepareStatement(deleteBookSql)
            ) {
                deleteQuantitiesStmt.setInt(1, id);
                deleteQuantitiesStmt.executeUpdate();

                deleteBookStmt.setInt(1, id);
                int affectedRows = deleteBookStmt.executeUpdate();

                conn.commit(); // Commit transaction
                return affectedRows > 0;
            } catch (SQLException e) {
                conn.rollback(); // Roll back transaction on error
                throw e;
            }
        }

    }

}


