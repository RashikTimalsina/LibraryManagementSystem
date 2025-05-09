package main.java.org.rashiktimalsina.services;
import main.java.org.rashiktimalsina.entities.LogEntry;
import main.java.org.rashiktimalsina.services.file_persistence.LogsDataService;
import main.java.org.rashiktimalsina.services.file_persistence.LogsDataServiceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * @author RashikTimalsina
 * @created 08/05/2025
 */
//Main Logging Service Class
public class LoggerService {
    //Set static to instance object since it can be created only once
    private static LoggerService instance;
    private final LogsDataService logsDataService;

    private LoggerService() {
        this.logsDataService = new LogsDataServiceImpl();
    }

    //Thread safe instance access
    public static synchronized LoggerService getInstance() {
        if (instance == null) {
            instance = new LoggerService();
        }
        return instance;
    }

    //Book related logs operation
    public void logBookOperation(String action, String details, String status) {
        logOperation(LogEntry.LogType.BOOK, action, details, status);
    }

    //User related logs operation
    public void logUserOperation(String action, String details, String status) {
        logOperation(LogEntry.LogType.USER, action, details, status);
    }

    //Transaction related logs operation
    public void logTransactionOperation(String action, String details, String status) {
        logOperation(LogEntry.LogType.TRANSACTION, action, details, status);
    }

    //System related logs operation
    public void logSystemOperation(String action, String details, String status) {
        logOperation(LogEntry.LogType.SYSTEM, action, details, status);
    }

    private void logOperation(LogEntry.LogType type, String action, String details, String status) {
        LogEntry log = new LogEntry(LocalDateTime.now(), type, action, details, status);
        try {
            //load all logs
            List<LogEntry> logs = logsDataService.loadLogEntries();
            if (logs == null) {
                logs = new ArrayList<>();
            }
            //otherwise add new log
            logs.add(log);
            //save logs in file
            logsDataService.saveLogEntries(logs);
        } catch (IOException e) {
            System.err.println("Failed to save log entry: " + e.getMessage());
        }
    }

    public void viewLogs(LogEntry.LogType type) {
        try {
            List<LogEntry> logs = logsDataService.loadLogEntries();
            if (logs == null || logs.isEmpty()) {
                System.out.println("No logs available.");
                return;
            }

            System.out.println("\n--- " + type + " LOGS ---");
            //filter logs and display
            for (LogEntry log : logs) {
                if (type == null || log.getType() == type) {
                    System.out.println(log);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load logs: " + e.getMessage());
        }
    }
}