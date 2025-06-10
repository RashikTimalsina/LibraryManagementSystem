package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.entities.Book;

/**
 * @author RashikTimalsina
 * @created 02/05/2025
 */
public interface BookQuantityService {

    void addBook(int bookId, int quantity);

    boolean isBookAvailable(int bookId);

    void decreaseQuantity(Book book);

    void increaseQuantity(Book book);

    int getQuantity(Book book);

}
