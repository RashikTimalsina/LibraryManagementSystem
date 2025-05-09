package main.java.org.rashiktimalsina.services.file_persistence;

import main.java.org.rashiktimalsina.entities.Book;
import main.java.org.rashiktimalsina.entities.LogEntry;
import main.java.org.rashiktimalsina.entities.Transaction;
import main.java.org.rashiktimalsina.entities.User;

import java.io.IOException;
import java.util.List;

/**
 * @author RashikTimalsina
 * @created 05/05/2025
 */
public interface LogsDataService {
    void saveBooks(List<Book> books) throws IOException;

    List<Book> loadBooks() throws IOException;

    void saveUsers(List<User> users) throws IOException;

    List<User> loadUsers() throws IOException;

    void saveTransactions(List<Transaction> transactions) throws IOException;

    List<Transaction> loadTransactions() throws IOException;

    void saveLogEntries(List<LogEntry> logEntries) throws IOException;

    List<LogEntry> loadLogEntries() throws IOException;


    <T> List<T> loadDataFromFile(String filename) throws IOException;
}