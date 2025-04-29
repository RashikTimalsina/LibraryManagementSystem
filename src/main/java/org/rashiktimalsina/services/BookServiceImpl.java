package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.entities.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 29/04/2025
 */

public class BookServiceImpl implements BookService {

    private final List<Book> books = new ArrayList<>();

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public Book findBookById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        String searchTitle = title.toLowerCase();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchTitle)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        String searchAuthor = author.toLowerCase();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(searchAuthor)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public void updateBookAvailability(String bookId, boolean available) {
        Book book = findBookById(bookId);
        if (book != null) {
            book.setAvailable(available);
        }
        else{
            System.out.println("Book not found with id: " + bookId);
        }
    }
}