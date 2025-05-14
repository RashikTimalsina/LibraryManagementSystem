package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.db.dao.BookQuantityDao;
import main.java.org.rashiktimalsina.entities.Book;
import main.java.org.rashiktimalsina.services.LoggerService;

import java.sql.SQLException;


/**
 * @author RashikTimalsina
 * @created 02/05/2025
 */
public class BookQuantityServiceImpl implements BookQuantityService {

//    private final Map<Book, Integer> bookQuantity = new HashMap<>();

    private final BookQuantityDao bookQuantityDao;
    private final LoggerService logger;

    public BookQuantityServiceImpl(BookQuantityDao bookQuantityDao) {
        this.bookQuantityDao = bookQuantityDao;
        this.logger = LoggerService.getInstance(); // Initialize LoggerService

    }

    @Override
    public void addBook(Book book, int quantity) {
//        bookQuantity.put(book, quantity);
        try {
            bookQuantityDao.addBook(book, quantity);
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }


    @Override
    public boolean isBookAvailable(Book book) {
//        return bookQuantity.getOrDefault(book, 0) > 0;
        try {
            return bookQuantityDao.isBookAvailable(book);
        } catch (Exception e) {
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public void decreaseQuantity(Book book) {
//        int current = bookQuantity.getOrDefault(book, 0);
//        if (current > 0) {
//            bookQuantity.put(book, current - 1);
//        }
        try {
            bookQuantityDao.decreaseQuantity(book);
        } catch (Exception e) {
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public void increaseQuantity(Book book) {
//        bookQuantity.put(book, bookQuantity.getOrDefault(book, 0) + 1);
        try {
            bookQuantityDao.increaseQuantity(book);
        } catch (Exception e) {
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public int getQuantity(Book book) {
//        return bookQuantity.getOrDefault(book, 0);
        try {
            return bookQuantityDao.getQuantity(book);
        } catch (Exception e) {
            throw new RuntimeException("Database error", e);
        }
        }
    }




