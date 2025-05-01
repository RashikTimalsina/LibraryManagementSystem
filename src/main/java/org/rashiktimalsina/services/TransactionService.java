package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.entities.Transaction;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 28/04/2025
 */

public interface TransactionService {

    void issueBook(Transaction transaction);

    boolean returnBook(String transactionId);

    List<Transaction> getAllTransactions();

    List<Transaction> getActiveTransactions();

    Transaction findTransactionById(String id);

    boolean deleteTransaction(String transactionId);

}