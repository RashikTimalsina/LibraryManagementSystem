package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.entities.Book;

/**
 * @author RashikTimalsina
 * @created 02/05/2025
 */
public interface BookQuantityService {

    void addBook(Book book, int quantity);

    boolean isBookAvailable(Book book);

    void decreaseQuantity(Book book);

    public void increaseQuantity(Book book);

    public int getQuantity(Book book);

}
