package main.java.org.rashiktimalsina.entities;

/**
 * @author RashikTimalsina
 * @created 18/05/2025
 */

public class BookQuantity {
    private int bookId;
    private int quantity;

    public BookQuantity() {
    }

    public BookQuantity(int bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BookQuantity{" +
                "bookId=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
}
