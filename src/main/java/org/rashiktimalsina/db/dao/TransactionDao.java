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
    public void issueBook(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (id, book_id, user_id, issue_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transaction.getId());
            stmt.setString(2, transaction.getBook().getId());
            stmt.setString(3, transaction.getUser().getId());
            stmt.setDate(4, Date.valueOf(transaction.getIssueDate()));
            stmt.executeUpdate();
        }
    }

    public boolean returnBook(String transactionId, LocalDate returnDate) throws SQLException {
        String sql = "UPDATE transactions SET return_date = ? WHERE id = ? AND return_date IS NULL";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(returnDate));
            stmt.setString(2, transactionId);
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
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getString("id"),
                        new Book(rs.getString("book_id"), rs.getString("book_title"), ""),
                        new User(rs.getString("user_id"), rs.getString("user_name"), ""),
                        rs.getDate("issue_date").toLocalDate()
                );
                if (rs.getDate("return_date") != null) {
                    transaction.setReturnDate(rs.getDate("return_date").toLocalDate());
                }
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
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getString("id"),
                        new Book(rs.getString("book_id"), rs.getString("book_title"), ""),
                        new User(rs.getString("user_id"), rs.getString("user_name"), ""),
                        rs.getDate("issue_date").toLocalDate()
                ));
            }
        }
        return transactions;
    }

    public Transaction findTransactionById(String id) throws SQLException {
        String sql = "SELECT t.*, b.title as book_title, u.name as user_name " +
                "FROM transactions t " +
                "JOIN books b ON t.book_id = b.id " +
                "JOIN users u ON t.user_id = u.id " +
                "WHERE t.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Transaction transaction = new Transaction(
                            rs.getString("id"),
                            new Book(rs.getString("book_id"), rs.getString("book_title"), ""),
                            new User(rs.getString("user_id"), rs.getString("user_name"), ""),
                            rs.getDate("issue_date").toLocalDate()
                    );
                    if (rs.getDate("return_date") != null) {
                        transaction.setReturnDate(rs.getDate("return_date").toLocalDate());
                    }
                    return transaction;
                }
            }
        }
        return null;
    }

    public boolean deleteTransaction(String transactionId) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            return stmt.executeUpdate() > 0;
        }
    }
}

