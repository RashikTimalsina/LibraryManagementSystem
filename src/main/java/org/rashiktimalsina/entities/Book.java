package main.java.org.rashiktimalsina.entities;

import java.io.Serializable;

/**
 * @author RashikTimalsina
 * @created 28/04/2025
 */

public class Book implements Serializable {
    private int id;
    private String title;
    private String author;

    //since id is auto-incremented
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(int generatedId) {
        id = generatedId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book ID: " + id + ", Title: " + title + ", Author: " + author;
    }

}