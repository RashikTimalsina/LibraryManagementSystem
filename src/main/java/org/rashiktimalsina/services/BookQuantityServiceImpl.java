package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.entities.Book;

import java.util.HashMap;
import java.util.Map;

/**
 * @author RashikTimalsina
 * @created 02/05/2025
 */
public class BookQuantityServiceImpl implements BookQuantityService {

    private final Map<Book, Integer> bookQuantity = new HashMap<>();

    @Override
    public void addBook(Book book, int quantity) {
        bookQuantity.put(book, quantity);
    }

    @Override
    public boolean isBookAvailable(Book book) {
        return bookQuantity.getOrDefault(book, 0) > 0;
    }

    @Override
    public void decreaseQuantity(Book book) {
        int current = bookQuantity.getOrDefault(book, 0);
        if (current > 0) {
            bookQuantity.put(book, current - 1);
        }
    }

    @Override
    public void increaseQuantity(Book book) {
        bookQuantity.put(book, bookQuantity.getOrDefault(book, 0) + 1);
    }

    @Override
    public int getQuantity(Book book) {
        return bookQuantity.getOrDefault(book, 0);
    }
}



