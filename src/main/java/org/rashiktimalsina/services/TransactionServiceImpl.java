package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.entities.Transaction;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 29/04/2025
 */
public class TransactionServiceImpl implements TransactionService {

    private final List<Transaction> transactions = new ArrayList<>();
    private final BookQuantityService bookQuantityService;
    private final BookService bookService;

    public TransactionServiceImpl(BookQuantityService bookQuantityService, BookService bookService) {
        this.bookQuantityService = bookQuantityService;
        this.bookService = bookService;
    }

    @Override
    public void issueBook(Transaction transaction) {
        if (bookQuantityService.getQuantity(transaction.getBook()) > 0) {
            transactions.add(transaction);
            bookQuantityService.decreaseQuantity(transaction.getBook());
        } else {
            throw new IllegalStateException("Book not available");
        }
    }

    @Override
    public boolean returnBook(String transactionId) {
        Transaction transaction = findTransactionById(transactionId);
        if (transaction != null && transaction.getReturnDate() == null) {
            transaction.setReturnDate(LocalDate.now());
            bookQuantityService.increaseQuantity(transaction.getBook());
        }
        return false;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    @Override
    public List<Transaction> getActiveTransactions() {
        List<Transaction> activeTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getReturnDate() == null) {
                activeTransactions.add(transaction);
            }
        }
        return activeTransactions;
    }

    @Override
    public Transaction findTransactionById(String id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(id)) {
                return transaction;
            }
        }
        return null;
    }

    @Override
    public boolean deleteTransaction(String transactionId) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId().equals(transactionId)) {
                if (transactions.get(i).getReturnDate() == null) {
                    bookQuantityService.increaseQuantity(transactions.get(i).getBook());
                }
                transactions.remove(i);
                return true;
            }
        }
        return false;
    }
}