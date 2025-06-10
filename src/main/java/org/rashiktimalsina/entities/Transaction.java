package main.java.org.rashiktimalsina.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author RashikTimalsina
 * @created 28/04/2025
 */

public class Transaction implements Serializable {
    private int id;
    private Book book;
    private User user;
    private LocalDate issueDate;
    private LocalDate returnDate;

    //id is auto-incremented
    public Transaction(Book book, User user, LocalDate issueDate, LocalDate returnDate) {
        this.book = Objects.requireNonNull(book);
        this.user = Objects.requireNonNull(user);
        this.issueDate = Objects.requireNonNull(issueDate);
        this.returnDate = returnDate;
    }

    public Transaction(int id, Book book, User user, LocalDate issueDate, LocalDate returnDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setId(int generatedId) {
        id = generatedId;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + id +
                "\nBook: " + book.getTitle() +
                "\nUser: " + user.getName() +
                "\nIssued on: " + issueDate +
                "\nReturned on: " + (returnDate != null ? returnDate : "Not returned yet");
    }
}