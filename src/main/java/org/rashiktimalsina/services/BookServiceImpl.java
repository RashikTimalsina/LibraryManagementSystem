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
    private final BookQuantityService bookQuantityService;
    private final String FILE_NAME = "books.txt";

    public BookServiceImpl(BookQuantityService bookQuantityService) {
        this.bookQuantityService = bookQuantityService;
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
        bookQuantityService.addBook(book, 1);
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
        // to search title, first we need to make sure the text is in lowercase form
        String searchTitle = title.toLowerCase();
        for (Book book : books) {
            //fetching the book title in l-c form and then checking if it matches with search title
            if (book.getTitle().toLowerCase().contains(searchTitle)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        //same logic as above , only difference is the author in place of title
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
        // traverse through all the books in the list to see the available books
        for (Book book : books) {
            if (bookQuantityService.getQuantity(book) > 0) {
                result.add(book);   //add and display the available books in the result
            }
        }
        return result;
    }

//    @Override
//    public void updateBookAvailability(String bookId, boolean available) {
//        Book book = findBookById(bookId);
//        // first , if book exists in the list, mark it as available.
//        if (book != null) {
//            book.setAvailable(available);
//        }
//        //otherwise, give a not found message
//        else{
//            System.out.println("Book not found with id: " + bookId);
//        }
//    }
}