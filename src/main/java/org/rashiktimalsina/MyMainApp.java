package main.java.org.rashiktimalsina;

import main.java.org.rashiktimalsina.entities.Book;
import main.java.org.rashiktimalsina.entities.User;
import main.java.org.rashiktimalsina.entities.Transaction;
import main.java.org.rashiktimalsina.services.BookService;
import main.java.org.rashiktimalsina.services.BookServiceImpl;
import main.java.org.rashiktimalsina.services.TransactionService;
import main.java.org.rashiktimalsina.services.TransactionServiceImpl;
import main.java.org.rashiktimalsina.services.UserService;
import main.java.org.rashiktimalsina.services.UserServiceImpl;
import main.java.org.rashiktimalsina.utils.IdGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * @author RashikTimalsina
 * @created 29/04/2025
 */

public class MyMainApp {
    private static BookService bookService = new BookServiceImpl();
    private static UserService userService = new UserServiceImpl();
    private static TransactionService transactionService = new TransactionServiceImpl(bookService);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n-----------------------------------------------");
            System.out.println("--- Welcome to Library Management System ---");
            System.out.println("-----------------------------------------------");
            System.out.println("1. Book Operations");
            System.out.println("2. User Operations");
            System.out.println("3. Transaction Operations");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int mainChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

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
                    System.out.println("Exiting system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
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
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

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
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addNewBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        Book book = new Book(IdGenerator.generateBookId(), title, author);
        bookService.addBook(book);
        System.out.println("Book added: " + book);
    }

    private static void viewAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\nAll Books:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private static void searchBookByTitle() {
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();
        List<Book> books = bookService.findBooksByTitle(title);

        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\nSearch Results:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private static void searchBookByAuthor() {
        System.out.print("Enter author to search: ");
        String author = scanner.nextLine();
        List<Book> books = bookService.findBooksByAuthor(author);

        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\nSearch Results:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private static void viewAvailableBooks() {
        List<Book> books = bookService.getAvailableBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("\nAvailable Books:");
            for (Book book : books) {
                System.out.println(book);
            }
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

            int choice = scanner.nextInt();
            scanner.nextLine();

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
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        User user = new User(IdGenerator.generateUserId(), name, email);
        userService.addUser(user);
        System.out.println("User added: " + user);
    }

    private static void viewAllUsers() {
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

    private static void updateUser(){

            //First show all users
            viewAllUsers();
            System.out.print("\nEnter User ID to update: ");
            String userId = scanner.nextLine();

            // Check if user exists first
            User existingUser = userService.findUserById(userId);
            if (existingUser == null) {
                System.out.println("Error: User with ID " + userId + " not found.");
                return;
            }

            System.out.println("\nCurrent User Details:");
            System.out.println(existingUser);

            System.out.print("\nEnter new name (leave blank to keep current): ");
            String newName = scanner.nextLine();

            System.out.print("Enter new email (leave blank to keep current): ");
            String newEmail = scanner.nextLine();

            // Only update fields that have new values
            String finalName = newName.isBlank() ? existingUser.getName() : newName;
            String finalEmail = newEmail.isBlank() ? existingUser.getEmail() : newEmail;

            boolean isUpdated = userService.updateUser(userId, finalName, finalEmail);
            if (isUpdated) {
                System.out.println("\nUser updated successfully!");
                System.out.println("Updated Details:");
                System.out.println(userService.findUserById(userId));
            } else {
                System.out.println("Error: Failed to update user.");
            }
        }

    private static void deleteUser(){

            //First need to view all users
            viewAllUsers();
            System.out.print("\nEnter User ID to delete: ");
            String userId = scanner.nextLine();

            // Confirm deletion
            System.out.print(" Want to delete this user? (yes/no): ");
            String confirmation = scanner.nextLine().toLowerCase();

            if (confirmation.equals("yes")) {
                boolean isDeleted = userService.deleteUser(userId);
                if (isDeleted) {
                    System.out.println("User deleted successfully!");
                } else {
                    System.out.println("Error: User not found ");
                }
            } else {
                System.out.println("Deletion cancelled.");
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

            int choice = scanner.nextInt();
            scanner.nextLine();

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
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void issueBook() {
        // Show available books
        List<Book> availableBooks = bookService.getAvailableBooks();
        if (availableBooks.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        System.out.println("\nAvailable Books:");
        for (Book book : availableBooks) {
            System.out.println(book);
        }

        // Show users
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users registered.");
            return;
        }

        System.out.println("\nRegistered Users:");
        for (User user : users) {
            System.out.println(user);
        }

        // Get input
        System.out.print("\nEnter Book ID: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        Book book = bookService.findBookById(bookId);
        User user = userService.findUserById(userId);

        if (book == null || user == null) {
            System.out.println("Invalid book or user ID.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book not available.");
            return;
        }

        Transaction transaction = new Transaction(
                IdGenerator.generateTransactionId(),
                book,
                user,
                LocalDate.now()
        );

        transactionService.issueBook(transaction);
        System.out.println("Book issued. Transaction ID: " + transaction.getId());
    }

    private static void returnBook() {
        List<Transaction> activeTransactions = transactionService.getActiveTransactions();
        if (activeTransactions.isEmpty()) {
            System.out.println("No active transactions.");
            return;
        }

        System.out.println("\nActive Transactions:");
        for (Transaction t : activeTransactions) {
            System.out.println(t.getId() + " - " + t.getBook().getTitle() +
                    " issued to " + t.getUser().getName());
        }

        System.out.print("\nEnter Transaction ID: ");
        String transactionId = scanner.nextLine();

        transactionService.returnBook(transactionId);
        System.out.println("Book returned.");
    }

    private static void viewAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions.");
        } else {
            System.out.println("\nAll Transactions:");
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }

    private static void deleteTransaction() {
        //To delete first show all transactions
        viewAllTransactions();

        System.out.print("\nEnter Transaction ID to delete: ");
        String transactionId = scanner.nextLine();

        boolean deleted = transactionService.deleteTransaction(transactionId);
        if (deleted) {
            System.out.println("Transaction deleted successfully.");
        } else {
            System.out.println("Transaction not found.Try again");
        }
    }
}