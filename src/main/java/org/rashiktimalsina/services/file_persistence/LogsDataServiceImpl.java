package main.java.org.rashiktimalsina.services.file_persistence;

import main.java.org.rashiktimalsina.entities.Book;
import main.java.org.rashiktimalsina.entities.LogEntry;
import main.java.org.rashiktimalsina.entities.Transaction;
import main.java.org.rashiktimalsina.entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RashikTimalsina
 * @created 05/05/2025
 */
public class LogsDataServiceImpl implements LogsDataService {
    //Creating a file path constants
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + "/users.dat";
    private static final String BOOKS_FILE = DATA_DIR + "/books.dat";
    private static final String TRANSACTIONS_FILE = DATA_DIR + "/transactions.dat";
    private static final String LOGS_FILE = DATA_DIR + "/logs.dat";

    public LogsDataServiceImpl() {
        //Make a new directory 'data' if it doesn't exist
        new File(DATA_DIR).mkdirs();
    }

    @Override
    public void saveBooks(List<Book> books) throws IOException {
        saveToFile(books, BOOKS_FILE);
    }

    @Override
    public List<Book> loadBooks() throws IOException {
        return loadDataFromFile(BOOKS_FILE);
    }


    @Override
    public void saveUsers(List<User> users) throws IOException {
        saveToFile(users, USERS_FILE);
    }

    @Override
    public List<User> loadUsers() throws IOException {
        return loadDataFromFile(USERS_FILE);
    }


    @Override
    public void saveTransactions(List<Transaction> transactions) throws IOException {
        saveToFile(transactions, TRANSACTIONS_FILE);
    }

    @Override
    public List<Transaction> loadTransactions() throws IOException {
        return loadDataFromFile(TRANSACTIONS_FILE);
    }

    @Override
    public void saveLogEntries(List<LogEntry> logEntries) throws IOException {
        saveToFile(logEntries, LOGS_FILE);

        //Create a HashMap object logsByType for text file output
        Map<LogEntry.LogType, List<LogEntry>> logsByType = new HashMap<>();
        for (LogEntry log : logEntries) {
            LogEntry.LogType type = log.getType();
            if (!logsByType.containsKey(type)) {
                logsByType.put(type, new ArrayList<>());
            }
            logsByType.get(type).add(log);
        }

        //Append logs to specific type(BOOKS || USERS || TRANSACTIONS) text files
        for (Map.Entry<LogEntry.LogType, List<LogEntry>> entry : logsByType.entrySet()) {
            String typeFilename = LOGS_FILE + "_" + entry.getKey() + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(typeFilename, true))) {
                for (LogEntry log : entry.getValue()) {
                    writer.println(log.toString());
                }
            }
        }
    }

    @Override
    public List<LogEntry> loadLogEntries() throws IOException {
        return loadDataFromFile(LOGS_FILE);
    }

    @Override
    public <T> List<T> loadDataFromFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error during deserialization", e);
        }
    }

    private <T> void saveToFile(List<T> items, String filename) throws IOException {
        //Serialize object list to file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(items);
        }
    }
}





