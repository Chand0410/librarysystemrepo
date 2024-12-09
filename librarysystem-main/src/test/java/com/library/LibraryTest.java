package com.library;

import com.library.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        // Initialize the library and books before each test
        library = new Library();
        book1 = new Book(1, "Spring in Action", "Craig Walls", "Programming");
        book2 = new Book(2, "Effective Java", "Joshua Bloch", "Programming");
        library.addBook(book1.getId(), book1.getTitle(), book1.getAuthor(), book1.getCategory());
    }

    @Test
    void testAddBook() {
        // Test adding a book
        library.addBook(book2.getId(), book2.getTitle(), book2.getAuthor(), book2.getCategory());
        Book foundBook = library.findBookById(book2.getId());
        assertNotNull(foundBook);
        assertEquals(book2.getTitle(), foundBook.getTitle());
    }

    @Test
    void testAddDuplicateBook() {
        // Test adding a duplicate book (same ID)
        library.addBook(book1.getId(), book1.getTitle(), book1.getAuthor(), book1.getCategory());
        int initialSize = library.getBooks().size();
        library.addBook(book1.getId(), book1.getTitle(), book1.getAuthor(), book1.getCategory());
        int newSize = library.getBooks().size();
        assertEquals(initialSize, newSize);  // Size should not change
    }

    @Test
    void testDeleteBook() {
        // Test deleting a book
        library.deleteBook(book1.getId());
        Book deletedBook = library.findBookById(book1.getId());
        assertNull(deletedBook);  // The book should be null after deletion
    }

    @Test
    void testDeleteNonExistingBook() {
        // Test deleting a non-existing book
        library.deleteBook(999);  // ID that doesn't exist
        // Check for any output or log messages if needed
    }

    @Test
    void testIssueBook() {
        // Test issuing a book
        library.issueBook(book1.getId());
        Book foundBook = library.findBookById(book1.getId());
        assertTrue(foundBook.isIssued());  // The book should be issued
    }

    @Test
    void testReturnBook() {
        // Test returning a book
        library.issueBook(book1.getId());
        library.returnBook(book1.getId());
        assertFalse(book1.isIssued());  // The book should be available now
    }

    @Test
    void testSearchByTitle() {
        // Test searching by title
        library.addBook(book2.getId(), book2.getTitle(), book2.getAuthor(), book2.getCategory());
        var results = library.searchByTitle("Spring");
        assertEquals(1, results.size());  // Should find one book with title "Spring in Action"
    }

    @Test
    void testSearchByAuthor() {
        // Test searching by author
        library.addBook(book2.getId(), book2.getTitle(), book2.getAuthor(), book2.getCategory());
        var results = library.searchByAuthor("Joshua Bloch");
        assertEquals(1, results.size());  // Should find one book by "Joshua Bloch"
    }

    @Test
    void testDisplayAllBooks() {
        // Test displaying all books
        library.addBook(book2.getId(), book2.getTitle(), book2.getAuthor(), book2.getCategory());
        library.displayAllBooks();  // Ensure it prints the list of books correctly
        // No assert here as it's checking console output, you can capture output in a real scenario if needed
    }

    @Test
    void testSearchBookById() {
        // Test searching for a book by ID
        library.searchBookById(book1.getId());
        // Output will be printed to the console, so no assert is used here.
    }

    @Test
    void testSaveAndLoadFromFile() {
        // Test saving and loading from file
        String filename = "library_data.txt";
        library.saveToFile(filename);
        Library newLibrary = new Library();
        newLibrary.loadFromFile(filename);
        Book loadedBook = newLibrary.findBookById(book1.getId());
        assertNotNull(loadedBook);  // Check if the book is loaded correctly
    }
}
