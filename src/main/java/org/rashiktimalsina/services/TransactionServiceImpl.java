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

    private final BookService bookService;

    public TransactionServiceImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void issueBook(Transaction transaction) {
        transactions.add(transaction);
        bookService.updateBookAvailability(transaction.getBook().getId(), false);
    }

    @Override
    public boolean returnBook(String transactionId) {
        Transaction transaction = findTransactionById(transactionId);
        if (transaction != null && transaction.getReturnDate() == null) {
            transaction.setReturnDate(LocalDate.now());
            bookService.updateBookAvailability(transaction.getBook().getId(), true);
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
                    bookService.updateBookAvailability(
                            transactions.get(i).getBook().getId(),
                            true
                    );
                }
                transactions.remove(i);
                return true;
            }
        }
        return false;
    }
}