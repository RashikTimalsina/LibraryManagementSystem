package main.java.org.rashiktimalsina.utils;

/**
 * @author RashikTimalsina
 * @created 29/04/2025
 */

public class IdGenerator {
    private static int bookCounter = 1;
    private static int userCounter = 1;
    private static int transactionCounter = 1;

    public static String generateBookId() {
        return "Book" + bookCounter++;
    }

    public static String generateUserId() {
        return "User" + userCounter++;
    }

    public static String generateTransactionId() {
        return "Transaction" + transactionCounter++;
    }
}