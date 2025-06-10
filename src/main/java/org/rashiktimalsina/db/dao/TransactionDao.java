package main.java.org.rashiktimalsina.db.dao;

import main.java.org.rashiktimalsina.db.connection.DatabaseConnection;
import main.java.org.rashiktimalsina.entities.Transaction;
import main.java.org.rashiktimalsina.entities.Book;
import main.java.org.rashiktimalsina.entities.User;


import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


/**
 * @author RashikTimalsina
 * @created 11/05/2025
 */
public class TransactionDao {
    public int issueBook(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (book_id, user_id, issue_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, transaction.getBook().getId());
            stmt.setInt(2, transaction.getUser().getId());
            stmt.setDate(3, Date.valueOf(transaction.getIssueDate()));
            stmt.executeUpdate();


            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new SQLException("Failed to get generated transaction ID");
        }
    }

    public boolean returnBook(int transactionId, LocalDate returnDate) throws SQLException {
        String sql = "UPDATE transactions SET return_date = ? WHERE id = ? AND return_date IS NULL";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(returnDate));
            stmt.setInt(2, transactionId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, b.title as book_title, u.name as user_name " +
                "FROM transactions t " +
                "JOIN books b ON t.book_id = b.id " +
                "JOIN users u ON t.user_id = u.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                LocalDate returnDate = rs.getDate("return_date") != null ?
                        rs.getDate("return_date").toLocalDate() : null;
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        new Book(rs.getInt("book_id"), rs.getString("book_title"), ""),
                        new User(rs.getInt("user_id"), rs.getString("user_name"), ""),
                        rs.getDate("issue_date").toLocalDate(),
                        returnDate
                );
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public List<Transaction> getActiveTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, b.title as book_title, u.name as user_name " +
                "FROM transactions t " +
                "JOIN books b ON t.book_id = b.id " +
                "JOIN users u ON t.user_id = u.id " +
                "WHERE t.return_date IS NULL";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                LocalDate returnDate = rs.getDate("return_date") != null ?
                        rs.getDate("return_date").toLocalDate() : null;
                transactions.add(new Transaction(
                        rs.getInt("id"),
                        new Book(rs.getInt("book_id"), rs.getString("book_title"), ""),
                        new User(rs.getInt("user_id"), rs.getString("user_name"), ""),
                        rs.getDate("issue_date").toLocalDate(),
                        returnDate
                ));
            }
        }
        return transactions;
    }

    public Transaction findTransactionById(int id) throws SQLException {
        String sql = "SELECT t.*, b.title as book_title, u.name as user_name " +
                "FROM transactions t " +
                "JOIN books b ON t.book_id = b.id " +
                "JOIN users u ON t.user_id = u.id " +
                "WHERE t.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate returnDate = rs.getDate("return_date") != null ?
                            rs.getDate("return_date").toLocalDate() : null;
                    Transaction transaction = new Transaction(
                            rs.getInt("id"),
                            new Book(rs.getInt("book_id"), rs.getString("book_title"), ""),
                            new User(rs.getInt("user_id"), rs.getString("user_name"), ""),
                            rs.getDate("issue_date").toLocalDate(),
                            returnDate
                    );

                    return transaction;
                }
            }
        }
        return null;
    }

    public boolean deleteTransaction(int transactionId) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            return stmt.executeUpdate() > 0;
        }
    }
}

