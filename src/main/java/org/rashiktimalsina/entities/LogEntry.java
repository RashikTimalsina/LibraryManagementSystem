package main.java.org.rashiktimalsina.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author RashikTimalsina
 * @created 05/05/2025
 */
public class LogEntry<T> implements Serializable {
   //create a custom datatypes with enum
    public enum LogType {
        BOOK,
       USER,
       TRANSACTION,
       SYSTEM
    }

    private LocalDateTime timestamp;
    private LogType type;
    private String action;
    private String details;
    private String status;

    public LogEntry(LocalDateTime timestamp, LogType type, String action, String details, String status) {
        this.timestamp = timestamp;
        this.type = type;
        this.action = action;
        this.details = details;
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LogType getType() {
        return type;
    }

    public String getAction() {
        return action;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "[" + timestamp + "][" + type + "][" + status + "] " + action + " - " + details;
    }

}