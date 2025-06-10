package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.entities.Book;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 28/04/2025
 */

public interface BookService {

    void addBook(Book book);

    List<Book> getAllBooks();

    Book findBookById(int id);

    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByAuthor(String author);

    List<Book> getAvailableBooks();

    boolean deleteBook(int id);



}