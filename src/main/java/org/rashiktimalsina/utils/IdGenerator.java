package main.java.org.rashiktimalsina.utils;

import java.util.UUID;

public class IdGenerator {
    public static String generateBookId() {
        return "B-" + UUID.randomUUID().toString();
    }

    public static String generateUserId() {
        return "U-" + UUID.randomUUID().toString();
    }

    public static String generateTransactionId() {
        return "T-" + UUID.randomUUID().toString();
    }
}