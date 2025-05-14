package main.java.org.rashiktimalsina;

import main.java.org.rashiktimalsina.db.connection.DatabaseConnection;
import main.java.org.rashiktimalsina.db.dao.BookDao;
import main.java.org.rashiktimalsina.db.dao.BookQuantityDao;
import main.java.org.rashiktimalsina.db.dao.TransactionDao;
import main.java.org.rashiktimalsina.db.dao.UserDao;
import main.java.org.rashiktimalsina.entities.Book;
import main.java.org.rashiktimalsina.entities.LogEntry;
import main.java.org.rashiktimalsina.entities.User;
import main.java.org.rashiktimalsina.entities.Transaction;
import main.java.org.rashiktimalsina.services.*;
import main.java.org.rashiktimalsina.utils.IdGenerator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * @author RashikTimalsina
 * @created 29/04/2025
 */

public class MyMainApp {
    // Database-backed services
    private static BookQuantityDao bookQuantityDao;
    private static BookDao bookDao;
    private static UserDao userDao;
    private static TransactionDao transactionDao;

    //Entity-based services
    private static BookQuantityService bookQuantityService;
    private static BookService bookService;
    private static UserService userService;
    private static TransactionService transactionService;

    //For logging
    private static LoggerService logger;

    //create scanner object to take input
     static Scanner sc = new Scanner(System.in);


    //--------------------------------------------------------------------

    //MAIN METHOD
    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection().close();
            System.out.println("Database connection established successfully!");

            // Initialize DAOs
            bookQuantityDao = new BookQuantityDao();
            bookDao = new BookDao();
            userDao = new UserDao();
            transactionDao = new TransactionDao();

            // Initialize services with DAOs
            bookQuantityService = new BookQuantityServiceImpl(bookQuantityDao);
            bookService = new BookServiceImpl(bookDao, bookQuantityDao);
            userService = new UserServiceImpl(userDao);
            transactionService = new TransactionServiceImpl(transactionDao, bookQuantityDao);


            // Then initialize logger
            logger = LoggerService.getInstance();

            System.out.println("Data files and logger initialized successfully!");

            while (true) {
                System.out.println("\n-----------------------------------------------");
                System.out.println("--- Welcome to Library Management System ---");
                System.out.println("-----------------------------------------------");
                System.out.println("1. Book Operations");
                System.out.println("2. User Operations");
                System.out.println("3. Transaction Operations");
                System.out.println("4. View logs");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int mainChoice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (mainChoice) {
                    case 1:
                        bookOperations();
                        break;
                    case 2:
                        userOperations();
                        break;
                    case 3:
                        transactionOperations();
                        break;
                    case 4:
                        viewLogs();
                        break;
                    case 5:
                        System.out.println("Exiting system.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Try again.");
                 }
               }
             } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Application error: " + e.getMessage());
            System.exit(1);
        }
}


    // BOOK OPERATIONS
    private static void bookOperations() {
        while (true) {
            System.out.println("\n--- Book Operations ---");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search by Title");
            System.out.println("4. Search by Author");
            System.out.println("5. View Available Books");
            System.out.println("6. Delete Book");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addNewBook();
                    break;
                case 2:
                    viewAllBooks();
                    break;
                case 3:
                    searchBookByTitle();
                    break;
                case 4:
                    searchBookByAuthor();
                    break;
                case 5:
                    viewAvailableBooks();
                    break;
                case 6:
                    deleteBook();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addNewBook() {
        try{
        System.out.print("Enter book title: ");
        String title = sc.nextLine();
        System.out.print("Enter author: ");
        String author = sc.nextLine();
        System.out.print("Enter initial quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();


        // creating a book object which consists of autogenerated-id , title and author as an args
        Book book = new Book(IdGenerator.generateBookId(), title, author);
        //adding the user object using add method defined inside userService class
        bookService.addBook(book);
        bookQuantityService.addBook(book, quantity);

            logger.logBookOperation("ADD_BOOK",
                    "Added book: " + book + " with quantity: " + quantity,
                    "SUCCESS");
        System.out.println("Book added: " + book + " Quantity: " + quantity);
    }
        catch (Exception e) {
            logger.logBookOperation("ADD_BOOK",
                    "Failed to add book: " + e.getMessage(),
                    "ERROR");
            e.printStackTrace();
            System.out.println("Error adding book: " + e.getMessage());

        }
    }

    private static void viewAllBooks() {
        //fetch all books in a list calling getAllBooks method of bookService
        List<Book> books = bookService.getAllBooks();


        //check if the list if empty
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            // otherwise traverse through/ display all books
            System.out.println("\nAll Books:");
            for (Book book : books) {
                System.out.println(book + ", Available: " +
                        bookQuantityService.getQuantity(book));
            }
        }
    }

    private static void searchBookByTitle() {
        System.out.print("Enter title to search: ");
        String title = sc.nextLine();
        //call the findBooksByTitle method of bookService to search book by its title from the list
        List<Book> books = bookService.findBooksByTitle(title);
        //check if the list is empty
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            //otherwise iterate through and give the required book
            System.out.println("\nSearch Results:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private static void searchBookByAuthor() {
        System.out.print("Enter author to search: ");
        String author = sc.nextLine();
        //call the findBooksByAuthor method of bookService to search book by its author from the list
        List<Book> books = bookService.findBooksByAuthor(author);
        // check if the list is empty
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            //otherwise, iterate through and give the required book
            System.out.println("\nSearch Results:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private static void viewAvailableBooks() {
        //call the getAvailableBooks method of bookService to view the books in a list
        List<Book> books = bookService.getAvailableBooks();
        //check if it doesn't have book
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            //otherwise, traverse through and show the books
            System.out.println("\nAvailable Books:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }


    private static void deleteBook() {
        try {
            // First show all books
            viewAllBooks();

            System.out.print("\nBook ID to delete: ");
            String bookId = sc.nextLine();

            // Validate input
            if (bookId.isEmpty()) {
                System.out.println("Error: Book ID cannot be empty!");
                return;
            }

            // now check if book exists
            Book bookToDelete = bookService.findBookById(bookId);
            if (bookToDelete == null) {
                System.out.println("Error: Book with ID " + bookId + " does not exist!");
                return;
            }

            // show the book details before asking for confirmation
            System.out.println("\nBook to be deleted:");
            System.out.println(bookToDelete);

            //ask for confirmation
            System.out.print("\nAre you sure you want to delete this book? (yes/no): ");
            String confirmation = sc.nextLine().toLowerCase();

            if (confirmation.equalsIgnoreCase("yes")) {
                boolean isDeleted = bookService.deleteBook(bookId);
                if (isDeleted) {
                    System.out.println("Book deleted successfully!");
                    logger.logBookOperation("DELETE_BOOK",
                            "Deleted book: " + bookId,
                            "SUCCESS");
                } else {
                    System.out.println("Error: Failed to delete book.");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
        }catch(Exception e){
            System.out.println("Error during user deletion: " + e.getMessage());
            logger.logBookOperation("DELETE_BOOK",
                    "Failed to delete book: " + e.getMessage(),
                    "ERROR");
        }
    }


    // USER OPERATIONS
    private static void userOperations() {
        while (true) {
            System.out.println("\n--- User Operations ---");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addNewUser();
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addNewUser() {
        try{
        System.out.print("Enter user name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        // creating a user object which consists of autogenerated-id ,name and email as an args
        User user = new User(IdGenerator.generateUserId(), name, email);
        //adding the user object using add method defined inside userService class
        userService.addUser(user);

        logger.logUserOperation("ADD_USER",
                "Added user: " + user,
                "SUCCESS");
        System.out.println("User added: " + user);
    }catch (Exception e) {
            logger.logUserOperation("ADD_USER",
                    "Failed to add user: " + e.getMessage(),
                    "ERROR");
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    private static void viewAllUsers() {
        //fetch all users in a list calling getAllUsers method of userService
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("\nAll Users:");
            for (User user : users) {
                System.out.println(user);
            }
        }

    }

    private static void updateUser() {
try{
        //First show all users
        viewAllUsers();

        System.out.print("\nEnter User ID to update: ");
        String userId = sc.nextLine();

        // Check if prompted user exists first by calling findUserById method of us
        User existingUser = userService.findUserById(userId);
        //if it doesn't show any results then error
        if (existingUser == null) {
            System.out.println("Error: User with ID " + userId + " not found.");
            return;
        }
        //otherwise shows the current details of that user
        System.out.println("\nCurrent User Details:");
        System.out.println(existingUser);

        // update the username
        System.out.print("\nEnter new name: ");
        String newName = sc.nextLine();

        //update the email
        System.out.print("Enter new email: ");
        String newEmail = sc.nextLine();

        // Only update fields that have new values set
        String finalName = newName.isBlank() ? existingUser.getName() : newName;
        String finalEmail = newEmail.isBlank() ? existingUser.getEmail() : newEmail;

        //set the new/final details calling updateUser method of us
        boolean isUpdated = userService.updateUser(userId, finalName, finalEmail);
        if (isUpdated) {
            System.out.println("\nUser updated successfully!");
            System.out.println("Updated Details:");
            //calling findUserById method of us to show updated details
            System.out.println(userService.findUserById(userId));
            logger.logUserOperation("UPDATE_USER",
                    "Update user: " +userId,
                    "SUCCESS");
        } else {
            System.out.println("Error: Failed to update user.");
        }
    }
catch (Exception e) {
    logger.logUserOperation("ADD_USER",
            "Failed to add user: " + e.getMessage(),
            "ERROR");
   System.out.println("Error updating user: " + e.getMessage());
                }

    }

    private static void deleteUser() {
       try {
           // First show all users
           viewAllUsers();

           System.out.print("\nUser ID to delete: ");
           String userId = sc.nextLine();

           // Validate input
           if (userId.isEmpty()) {
               System.out.println("Error: User ID cannot be empty!");
               return;
           }

           // now check if user exists
           User userToDelete = userService.findUserById(userId);
           if (userToDelete == null) {
               System.out.println("Error: User with ID " + userId + " does not exist!");
               return;
           }

           // show the user details before asking for confirmation
           System.out.println("\nUser to be deleted:");
           System.out.println(userToDelete);

           //ask for confirmation
           System.out.print("\nAre you sure you want to delete this user? (yes/no): ");
           String confirmation = sc.nextLine().toLowerCase();

           if (confirmation.equalsIgnoreCase("yes")) {
               boolean isDeleted = userService.deleteUser(userId);
               if (isDeleted) {
                   System.out.println("User deleted successfully!");
                   logger.logUserOperation("DELETE_USER",
                           "Deleted user: " + userId,
                           "SUCCESS");
               } else {
                   System.out.println("Error: Failed to delete user.");
               }
           } else {
               System.out.println("Deletion cancelled.");
           }
       }catch(Exception e){
            System.out.println("Error during user deletion: " + e.getMessage());
           logger.logUserOperation("DELETE_USER",
                   "Failed to delete user: " + e.getMessage(),
                   "ERROR");
        }
    }




    // TRANSACTION OPERATIONS
    private static void transactionOperations() {
        while (true) {
            System.out.println("\n--- Transaction Operations ---");
            System.out.println("1. Issue Book");
            System.out.println("2. Return Book");
            System.out.println("3. View All Transactions");
            System.out.println("4. Delete Transaction");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    issueBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    viewAllTransactions();
                    break;
                case 4:
                    deleteTransaction();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void issueBook() {

        // first show available books calling getAvailableBooks() of bs
        List<Book> availableBooks = bookService.getAvailableBooks();
        //check if list is empty
        if (availableBooks.isEmpty()) {
            logger.logTransactionOperation("ISSUE_BOOK",
                    "No books available to issue",
                    "ERROR");
            System.out.println("No books available.");
            return;
        }
        //otherwise show the available books
        System.out.println("\nAvailable Books:");
        for (Book book : availableBooks) {
            System.out.println(book);
        }

        // second show registered users calling getAllUsers() of us
        List<User> users = userService.getAllUsers();
        //check if list is empty
        if (users.isEmpty()) {
            logger.logTransactionOperation("ISSUE_BOOK",
                    "No users registered",
                    "ERROR");
            System.out.println("No users registered.");
            return;
        }
        //otherwise show all registered users
        System.out.println("\nRegistered Users:");
        for (User user : users) {
            System.out.println(user);
        }


        //third get input of book id and user id to record the transaction
        System.out.print("\nEnter Book ID: ");
        String bookId = sc.nextLine();
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();

        //Call findBook & findUserById() of bs and us to check if they exists
        Book book = bookService.findBookById(bookId);
        User user = userService.findUserById(userId);

        //if shown empty throw a error message
        if (book == null || user == null) {
            logger.logTransactionOperation("ISSUE_BOOK",
                    "Invalid book or user ID. BookID: " + bookId + ", UserID: " + userId,
                    "ERROR");
            System.out.println("Invalid book or user ID.");
            return;
        }

        // creating a transaction object which consists of autogenerated-id , book object , user object and current localdate as an args
        Transaction transaction = new Transaction(
                IdGenerator.generateTransactionId(),
                book,
                user,
                LocalDate.now()
        );

        //invoke issueBook() of ts to issue a book after all these process
        try {
            transactionService.issueBook(transaction);
            logger.logTransactionOperation("ISSUE_BOOK",
                    "Issued book. Transaction: " + transaction.getId() +
                            ", Book: " + book.getTitle() +
                            ", User: " + user.getName(),
                    "SUCCESS");
            System.out.println("Book issued to Transaction ID: " + transaction.getId());
        } catch (IllegalStateException e) {
            logger.logTransactionOperation("ISSUE_BOOK",
                    "Failed to issue book: " + e.getMessage(),
                    "ERROR");
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void returnBook() {
       try{
        //  Get active transactions
        List<Transaction> activeTransactions = transactionService.getActiveTransactions();

        //  Check if any active transactions exist
        if (activeTransactions.isEmpty()) {
            System.out.println("No active transactions available.");
            return;
        }

        //  Display active transactions
        System.out.println("\nActive Transactions:");
        for (Transaction t : activeTransactions) {
            System.out.println("ID: " + t.getId() +
                    " | Book: " + t.getBook().getTitle() +
                    " | User: " + t.getUser().getName());
        }

        //  Get transaction ID from user
        System.out.print("\nEnter Transaction ID to return: ");
        String transactionId = sc.nextLine();

        //  Process return and show result
        if (transactionService.returnBook(transactionId)) {
            System.out.println("Book returned successfully for Transaction ID: " + transactionId);
        } else {
            Transaction transaction = transactionService.findTransactionById(transactionId);
            if (transaction == null) {
                System.out.println("Error: Transaction ID not found");
            } else if (transaction.getReturnDate() != null) {
                logger.logTransactionOperation("RETURN_BOOK",
                        "Returned transaction : " +transactionId ,
                        "SUCCESS");
                System.out.println("The book got returned successfully on " + transaction.getReturnDate());
            } else {
                System.out.println("Error: Unable to  return");
            }
        }

    }
       catch(Exception e){
           logger.logTransactionOperation("RETURN_BOOK",
                   "Failed to carry return transactions: " + e.getMessage(),
                   "ERROR");
           System.out.println("Error: " + e.getMessage());
       }
    }

    private static void viewAllTransactions() {
        //fetch all transactions in a list calling getAllTransactions() of transService
        List<Transaction> transactions = transactionService.getAllTransactions();
        //check if empty
        if (transactions.isEmpty()) {
            System.out.println("No transactions.");
        }
        //otherwise list all of them
        else {
            System.out.println("\nAll Transactions: ");
            for (Transaction t : transactions) {
                System.out.println(t + "");
            }
        }
    }

    private static void deleteTransaction() {
        try {
            //To delete first show all transactions
            viewAllTransactions();

            System.out.print("\nEnter Transaction ID to delete: ");
            String transactionId = sc.nextLine();

            //then check if transaction exists
            Transaction transaction = transactionService.findTransactionById(transactionId);

            if (transaction == null) {
                System.out.println("Transaction not found. Try again.");
                return;
            }

            // Check if it's an active transaction
            List<Transaction> activeTransactions = transactionService.getActiveTransactions();
            boolean isActive = false;

            for (Transaction active : activeTransactions) {
                if (active.getId().equals(transactionId)) {
                    isActive = true;
                    break;
                }
            }
            if (isActive) {
                System.out.println("Cannot delete: The book is still not returned.");
                return;
            }

            //invoke the deleteTransaction() of ts
            boolean deleted = transactionService.deleteTransaction(transactionId);
            //if deleted give success message
            if (deleted) {
                logger.logTransactionOperation("DELETE_TRANSACTION", "Deleted transaction : " +transactionId , "SUCCESS");
                System.out.println("Transaction deleted successfully.");
            } else {
                System.out.println("Transaction not found.Try again");
            }
        }
        catch(Exception e){
            logger.logTransactionOperation("DELETED_TRANSACTION",
                    "Failed to delete transaction: " + e.getMessage(),
                    "ERROR");
            System.out.println("Error: " + e.getMessage());
        }
    }



    //VIEW LOGS
    private static void viewLogs() {
        while (true) {
            System.out.println("\n--- Log Viewing ---");
            System.out.println("1. View Book Logs");
            System.out.println("2. View User Logs");
            System.out.println("3. View Transaction Logs");
            System.out.println("4. View All Logs");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    logger.viewLogs(LogEntry.LogType.BOOK);
                    break;
                case 2:
                    logger.viewLogs(LogEntry.LogType.USER);
                    break;
                case 3:
                    logger.viewLogs(LogEntry.LogType.TRANSACTION);
                    break;
                case 4:
                    logger.viewLogs(null); // to show all logs
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }

    }

    }

