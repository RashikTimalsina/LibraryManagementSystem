package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.db.dao.BookQuantityDao;
import main.java.org.rashiktimalsina.db.dao.TransactionDao;
import main.java.org.rashiktimalsina.entities.Transaction;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 29/04/2025
 */
public class TransactionServiceImpl implements TransactionService {

//    private final List<Transaction> transactions = new ArrayList<>();
    private final TransactionDao transactionDao;
    private final BookQuantityDao bookQuantityDao;
    private final LoggerService logger;



    public TransactionServiceImpl(TransactionDao transactionDao, BookQuantityDao bookQuantityDao) {
        this.transactionDao = transactionDao;
        this.bookQuantityDao = bookQuantityDao;
        this.logger = LoggerService.getInstance();
    }

    @Override
    public void issueBook(Transaction transaction) {
//        if (bookQuantityService.getQuantity(transaction.getBook()) > 0) {
//            transactions.add(transaction);
//            bookQuantityService.decreaseQuantity(transaction.getBook());
//        } else {
//            throw new IllegalStateException("Book not available");
//        }
        try {
            if (bookQuantityDao.getQuantity(transaction.getBook()) > 0) {
                transactionDao.issueBook(transaction);
                bookQuantityDao.decreaseQuantity(transaction.getBook());
                logger.logTransactionOperation("ISSUE_BOOK",
                        "Issued: " + transaction.getBook().getTitle(), "SUCCESS");
            } else {
                logger.logTransactionOperation("ISSUE_BOOK",
                        "Book not available: " + transaction.getBook().getTitle(), "ERROR");
                throw new IllegalStateException("Book not available");
            }
        } catch (SQLException e) {
            logger.logTransactionOperation("ISSUE_BOOK",
                    "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public boolean returnBook(String transactionId) {
//        Transaction transaction = findTransactionById(transactionId);
//        if (transaction != null && transaction.getReturnDate() == null) {
//            transaction.setReturnDate(LocalDate.now());
//            bookQuantityService.increaseQuantity(transaction.getBook());
//        }
//        return false;
        try {
            boolean returned = transactionDao.returnBook(transactionId, LocalDate.now());
            if (returned) {
                Transaction transaction = transactionDao.findTransactionById(transactionId);
                bookQuantityDao.increaseQuantity(transaction.getBook());
                logger.logTransactionOperation("RETURN_BOOK",
                        "Returned: " + transactionId, "SUCCESS");
            } else {
                logger.logTransactionOperation("RETURN_BOOK",
                        "Failed to return: " + transactionId, "ERROR");
            }
            return returned;
        } catch (SQLException e) {
            logger.logTransactionOperation("RETURN_BOOK",
                    "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
//        return new ArrayList<>(transactions);
        try {
            return transactionDao.getAllTransactions();
        } catch (SQLException e) {
            logger.logTransactionOperation("FETCH_TRANSACTIONS",
                    "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public List<Transaction> getActiveTransactions() {
//        List<Transaction> activeTransactions = new ArrayList<>();
//        for (Transaction transaction : transactions) {
//            if (transaction.getReturnDate() == null) {
//                activeTransactions.add(transaction);
//            }
//        }
//        return activeTransactions;
        try {
            return transactionDao.getActiveTransactions();
        } catch (SQLException e) {
            logger.logTransactionOperation("FETCH_ACTIVE_TRANS",
                    "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public Transaction findTransactionById(String id) {
//        for (Transaction transaction : transactions) {
//            if (transaction.getId().equals(id)) {
//                return transaction;
//            }
//        }
//        return null;
        try {
            return transactionDao.findTransactionById(id);
        } catch (SQLException e) {
            logger.logTransactionOperation("FIND_TRANSACTION",
                    "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public boolean deleteTransaction(String transactionId) {
//        for (int i = 0; i < transactions.size(); i++) {
//            if (transactions.get(i).getId().equals(transactionId)) {
//                if (transactions.get(i).getReturnDate() == null) {
//                    bookQuantityService.increaseQuantity(transactions.get(i).getBook());
//                }
//                transactions.remove(i);
//                return true;
//            }
//        }
//        return false;
//    }
        try {
            boolean deleted = transactionDao.deleteTransaction(transactionId);
            if (deleted) {
                logger.logTransactionOperation("DELETE_TRANSACTION",
                        "Deleted: " + transactionId, "SUCCESS");
            } else {
                logger.logTransactionOperation("DELETE_TRANSACTION",
                        "Failed to delete: " + transactionId, "ERROR");
            }
            return deleted;
        } catch (SQLException e) {
            logger.logTransactionOperation("DELETE_TRANSACTION",
                    "Failed: " + e.getMessage(), "ERROR");
            throw new RuntimeException("Database error", e);
        }
    }
}