package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.db.dao.BookDao;
import main.java.org.rashiktimalsina.db.dao.BookQuantityDao;

import main.java.org.rashiktimalsina.entities.Book;

import java.sql.SQLException;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 29/04/2025
 */

public class BookServiceImpl implements BookService {

//    private final List<Book> books = new ArrayList<>();

    private final BookDao bookDao;
    private final BookQuantityDao bookQuantityDao;
    private final LoggerService logger;


    public BookServiceImpl(BookDao bookDao, BookQuantityDao bookQuantityDao) {
        this.bookDao = bookDao;
        this.bookQuantityDao = bookQuantityDao;
        this.logger=LoggerService.getInstance();
    }

    @Override
    public void addBook(Book book) {
//        books.add(book);
        try {
            bookDao.addBook(book);
//            bookQuantityDao.addBook(book, 1);
            logger.logBookOperation("ADD_BOOK", "Added: " + book.getTitle(), "SUCCESS");
        } catch (SQLException e) {
            logger.logBookOperation("ADD_BOOK", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public Book findBookById(String id) {
//        for (Book book : books) {
//            if (book.getId().equals(id)) {
//                return book;
//            }
//        }
//        return null;
        try {
            return bookDao.findBookById(id);
        } catch (SQLException e) {
            logger.logBookOperation("FIND_BOOK", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }


    @Override
    public List<Book> getAllBooks() {
//        return new ArrayList<>(books);
        try {
            return bookDao.getAllBooks();
        } catch (SQLException e) {
            logger.logBookOperation("FETCH_BOOKS", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }


    @Override
    public List<Book> findBooksByTitle(String title) {
//        List<Book> result = new ArrayList<>();
//        // to search title, first we need to make sure the text is in lowercase form
//        String searchTitle = title.toLowerCase();
//        for (Book book : books) {
//            //fetching the book title in l-c form and then checking if it matches with search title
//            if (book.getTitle().toLowerCase().contains(searchTitle)) {
//                result.add(book);
//            }
//        }
//        return result;
        try {
            return bookDao.findBooksByTitle(title);
        } catch (SQLException e) {
            logger.logBookOperation("SEARCH_BOOKS", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
//        List<Book> result = new ArrayList<>();
//        //same logic as above , only difference is the author in place of title
//        String searchAuthor = author.toLowerCase();
//        for (Book book : books) {
//            if (book.getAuthor().toLowerCase().contains(searchAuthor)) {
//                result.add(book);
//            }
//        }
//        return result;
        try {
            return bookDao.findBooksByAuthor(author);
        } catch (SQLException e) {
            logger.logBookOperation("SEARCH_BOOKS", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }


    @Override
    public List<Book> getAvailableBooks() {
//        List<Book> result = new ArrayList<>();
//        // traverse through all the books in the list to see the available books
//        for (Book book : books) {
//            if (bookQuantityService.getQuantity(book) > 0) {
//                result.add(book);   //add and display the available books in the result
//            }
//        }
//        return result;
        try {
            List<Book> allBooks = bookDao.getAllBooks();
            allBooks.removeIf(book -> {
                try {
                    return bookQuantityDao.getQuantity(book) <= 0;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return allBooks;
        } catch (SQLException e) {
            logger.logBookOperation("FETCH_AVAILABLE", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public boolean deleteBook(String id) {

//        //Iterator to remove the selected book by its id
//        Iterator<Book> iterator = books.iterator();
//        while (iterator.hasNext()) {
//
//            Book book = iterator.next();
//            if (book.getId().equals(id)) {
//                iterator.remove();
//                return true;
//            }
//        }
//
//        return false;
//    }
        try {
            boolean deleted = bookDao.deleteBook(id);
            if (deleted) {
                logger.logUserOperation("DELETE_BOOK", "Deleted: " + id, "SUCCESS");
            } else {
                logger.logUserOperation("DELETE_BOOK", "Failed to delete: " + id, "ERROR");
            }
            return deleted;
        } catch (SQLException e) {
            logger.logUserOperation("DELETE_BOOK", "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }
}
