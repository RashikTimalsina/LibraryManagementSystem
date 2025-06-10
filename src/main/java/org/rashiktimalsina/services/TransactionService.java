package main.java.org.rashiktimalsina.services;

import main.java.org.rashiktimalsina.entities.Transaction;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 28/04/2025
 */

public interface TransactionService {

    void issueBook(Transaction transaction);

    boolean returnBook(int transactionId);

    List<Transaction> getAllTransactions();

    List<Transaction> getActiveTransactions();

    Transaction findTransactionById(int id);

    boolean deleteTransaction(int transactionId);

}