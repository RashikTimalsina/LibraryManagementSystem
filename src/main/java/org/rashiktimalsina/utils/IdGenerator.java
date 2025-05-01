package main.java.org.rashiktimalsina.utils;

/**
 * @author RashikTimalsina
 * @created 29/04/2025
 */

/*
    this class is created to give an auto generated id for all the entities involved for better readability and functioning.
* */
public class IdGenerator {
    private static int bookCounter = 1;
    private static int userCounter = 1;
    private static int transactionCounter = 1;

    //to generate id for book
    public static String generateBookId() {
        return "B" + bookCounter++;
    }

    //to generate id for user
    public static String generateUserId() {
        return "U" + userCounter++;
    }

    // to generate id for a transaction
    public static String generateTransactionId() {
        return "T" + transactionCounter++;
    }
}