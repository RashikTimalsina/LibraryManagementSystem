package main.java.org.rashiktimalsina.entities;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author RashikTimalsina
 * @created 28/04/2025
 */

public class Transaction implements Serializable {
    private String id;
    private Book book;
    private User user;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public Transaction(String id, Book book, User user, LocalDate issueDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.issueDate = issueDate;
    }

    public String getId() {
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

    public void setId(String id) {
        this.id = id;
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