package com.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList();
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);  // Return a new list to avoid modification of the original list
    }

    // Adds a book to the library if it doesn't already exist
    public void addBook(int id, String title, String author, String category) {
        if (findBookById(id) != null) {
            System.out.println("Book with ID " + id + " already exists.");
            return;
        }
        books.add(new Book(id, title, author, category));
        System.out.println("Book added successfully: " + title);
    }

    // Finds a book by its ID
    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    // Deletes a book from the library by its ID
    public void deleteBook(int id) {
        Book book = findBookById(id);
        if (book != null) {
            books.remove(book);
            System.out.println("Book with ID " + id + " has been deleted.");
        } else {
            System.out.println("Book with ID " + id + " not found.");
        }
    }

    // Issues a book by setting its 'isIssued' flag to true
    public void issueBook(int id) {
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + " not found.");
            return;
        }
        if (book.isIssued()) {
            System.out.println("Book \"" + book.getTitle() + "\" is already issued.");
        } else {
            book.setIssued(true);
            System.out.println("Book \"" + book.getTitle() + "\" has been issued.");
        }
    }

    // Returns a book by setting its 'isIssued' flag to false
    public void returnBook(int id) {
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + " not found.");
            return;
        }
        if (!book.isIssued()) {
            System.out.println("Book \"" + book.getTitle() + "\" is not currently issued.");
        } else {
            book.setIssued(false);
            System.out.println("Book \"" + book.getTitle() + "\" has been returned.");
        }
    }

    // Displays all books in the library
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        System.out.println("Library Books:");
        for (Book book : books) {
            System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() +
                    ", Author: " + book.getAuthor() + ", Category: " + book.getCategory() +
                    ", Status: " + (book.isIssued() ? "Issued" : "Available"));
        }
    }

    // Searches for books by title
    public List<Book> searchByTitle(String title) {
        List<Book> results = new ArrayList();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    // Searches for books by author
    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    // Searches for a book by ID and prints its details
    public void searchBookById(int id) {
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + " not found.");
        } else {
            System.out.println("Book Details:");
            System.out.println("ID: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Category: " + book.getCategory());
            System.out.println("Status: " + (book.isIssued() ? "Issued" : "Available"));
        }
    }

    // Searches for books by title and prints matching results
    public void searchBooksByTitle(String title) {
        List<Book> results = searchByTitle(title);
        if (results.isEmpty()) {
            System.out.println("No books found with title containing \"" + title + "\".");
        } else {
            System.out.println("Books matching title \"" + title + "\":");
            for (Book book : results) {
                System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle());
            }
        }
    }

    // Searches for books by author and prints matching results
    public void searchBooksByAuthor(String author) {
        List<Book> results = searchByAuthor(author);
        if (results.isEmpty()) {
            System.out.println("No books found by author \"" + author + "\".");
        } else {
            System.out.println("Books by \"" + author + "\":");
            for (Book book : results) {
                System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle());
            }
        }
    }

    // Saves the library data to a file
    public void saveToFile(String filename) {
        try{
            java.io.FileWriter outFile = new java.io.FileWriter(filename);
            for (Book book : books) {
                outFile.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + ","
                        + book.getCategory() + "," + (book.isIssued() ? "1" : "0") + "\n");
            }
            outFile.close();
            System.out.println("Library data saved to " + filename);
        } catch (java.io.IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    // Loads the library data from a file
    public void loadFromFile(String filename) {
        try {
            java.io.BufferedReader inFile = new java.io.BufferedReader(new java.io.FileReader(filename));
            String line;
            books.clear();
            while ((line = inFile.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    String category = parts[3];
                    boolean isIssued = parts[4].equals("1");
                    books.add(new Book(id, title, author, category));
                    books.get(books.size() - 1).setIssued(isIssued);
                }
            }
            System.out.println("Library data loaded from " + filename);
        } catch (java.io.IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Display menu options
        do {
            System.out.println("\nLibrary System");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display All Books");
            System.out.println("6. Search Book By ID");
            System.out.println("7. Search Books By Title");
            System.out.println("8. Search Books By Author");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: // Add Book
                    System.out.print("Enter Book ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Book Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Book Category: ");
                    String category = scanner.nextLine();
                    library.addBook(id, title, author, category);
                    break;

                case 2: // Delete Book
                    System.out.print("Enter Book ID to delete: ");
                    int deleteId = scanner.nextInt();
                    library.deleteBook(deleteId);
                    break;

                case 3: // Issue Book
                    System.out.print("Enter Book ID to issue: ");
                    int issueId = scanner.nextInt();
                    library.issueBook(issueId);
                    break;

                case 4: // Return Book
                    System.out.print("Enter Book ID to return: ");
                    int returnId = scanner.nextInt();
                    library.returnBook(returnId);
                    break;

                case 5: // Display All Books
                    library.displayAllBooks();
                    break;

                case 6: // Search Book By ID
                    System.out.print("Enter Book ID to search: ");
                    int searchId = scanner.nextInt();
                    library.searchBookById(searchId);
                    break;

                case 7: // Search Books By Title
                    System.out.print("Enter Title to search: ");
                    String searchTitle = scanner.nextLine(); // consume newline
                    library.searchBooksByTitle(searchTitle);
                    break;

                case 8: // Search Books By Author
                    System.out.print("Enter Author to search: ");
                    String searchAuthor = scanner.nextLine();
                    library.searchBooksByAuthor(searchAuthor);
                    break;

                case 9: // Exit
                    System.out.println("Exiting the Library System.");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 9); // Repeat until user chooses to exit

        scanner.close();
    }
}