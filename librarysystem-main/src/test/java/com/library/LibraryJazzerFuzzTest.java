package com.library;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.autofuzz.FuzzTarget;
import com.code_intelligence.jazzer.junit.FuzzTest;


public class LibraryJazzerFuzzTest {

    private static Library library = new Library();

    // Fuzz test for the addBook method

    @FuzzTest
    public static void fuzzAddBook(FuzzedDataProvider data) {
        int id = data.consumeInt(0, 10000);  // Random integer for book ID
        String title = data.consumeString(50); // Random string for title
        String author = data.consumeString(50); // Random string for author
        String category = data.consumeString(50);  // Random string for category

        library.addBook(id, title, author, category);

        // After adding the book, check if it was correctly added
        Book book = library.findBookById(id);
        if (book != null) {
            assertEquals(title, book.getTitle());
            assertEquals(author, book.getAuthor());
            assertEquals(category, book.getCategory());
        }
    }

    // Fuzz test for the deleteBook method
    @FuzzTest
    public static void fuzzDeleteBook(FuzzedDataProvider data) {
        int id = data.consumeInt(0, 10000);  // Random integer for book ID

        // Attempt to delete a book
        library.deleteBook(id);
    }

    // Fuzz test for the issueBook method
    @FuzzTest
    public static void fuzzIssueBook(FuzzedDataProvider data) {
        int id = data.consumeInt(0, 10000);  // Random integer for book ID

        // Attempt to issue a book
        library.issueBook(id);
    }

    // Fuzz test for the returnBook method
    @FuzzTest
    public static void fuzzReturnBook(FuzzedDataProvider data) {
        int id = data.consumeInt(0, 10000);  // Random integer for book ID

        // Attempt to return a book
        library.returnBook(id);
    }

    // Fuzz test for searching books by title
    @FuzzTest
    public static void fuzzSearchBooksByTitle(FuzzedDataProvider data) {
        String title = data.consumeString( 50);  // Random string for title search

        library.searchBooksByTitle(title);
    }

    // Fuzz test for searching books by author
    @FuzzTest
    public static void fuzzSearchBooksByAuthor(FuzzedDataProvider data) {
        String author = data.consumeString( 50);  // Random string for author search

        library.searchBooksByAuthor(author);
    }

    // Helper method to assert equality (since Jazzer doesn't have JUnit integration by default)
    private static void assertEquals(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + ", but was: " + actual);
        }
    }
}
