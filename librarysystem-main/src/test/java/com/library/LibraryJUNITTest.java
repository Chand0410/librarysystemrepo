package com.library;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class LibraryJUNITTest {

	private Library library;

	@Before
	public void setUp() {
		library = new Library();
	}

	@Test
	public void testAddBook() {
		library.addBook(1, "Book One", "Author A", "Fiction");
		Assert.assertNotNull(library.findBookById(1));
		Assert.assertEquals("Book One", library.findBookById(1).getTitle());
	}

	@Test
	public void testAddBookDuplicate() {
		library.addBook(1, "Book One", "Author A", "Fiction");
		library.addBook(1, "Duplicate Book", "Author B", "Non-Fiction");
		Assert.assertEquals("Book One", library.findBookById(1).getTitle());
	}

	@Test
	public void testDeleteBook() {
		library.addBook(1, "Book One", "Author A", "Fiction");
		library.deleteBook(1);
		Assert.assertNull(library.findBookById(1));
	}

	@Test
	public void testDeleteNonExistentBook() {
		library.deleteBook(999);
		// Ensure no exceptions occur; no assertion needed.
	}

	@Test
	public void testIssueBook() {
		library.addBook(1, "Book One", "Author A", "Fiction");
		library.issueBook(1);
		Assert.assertTrue(library.findBookById(1).isIssued());
	}

	@Test
	public void testIssueAlreadyIssuedBook() {
		library.addBook(1, "Book One", "Author A", "Fiction");
		library.issueBook(1);
		library.issueBook(1); // Test re-issuing the same book
		Assert.assertTrue(library.findBookById(1).isIssued());
	}

	@Test
	public void testReturnBook() {
		library.addBook(1, "Book One", "Author A", "Fiction");
		library.issueBook(1);
		library.returnBook(1);
		Assert.assertFalse(library.findBookById(1).isIssued());
	}

	@Test
	public void testReturnNonIssuedBook() {
		library.addBook(1, "Book One", "Author A", "Fiction");
		library.returnBook(1); // Test returning a book that wasn't issued
		Assert.assertFalse(library.findBookById(1).isIssued());
	}

	@Test
	public void testSearchByTitle() {
		library.addBook(1, "Java Programming", "Author A", "Education");
		library.addBook(2, "Effective Java", "Author B", "Education");
		List<Book> results = library.searchByTitle("Java");
		Assert.assertEquals(2, results.size());
	}

	@Test
	public void testSearchByAuthor() {
		library.addBook(1, "Book One", "Author A", "Fiction");
		library.addBook(2, "Book Two", "Author A", "Non-Fiction");
		List<Book> results = library.searchByAuthor("Author A");
		Assert.assertEquals(2, results.size());
	}

	@Test
	public void testDisplayAllBooks() {
		library.addBook(1, "Book One", "Author A", "Fiction");
		library.addBook(2, "Book Two", "Author B", "Non-Fiction");
		library.displayAllBooks();
		// Check console output manually if needed or mock System.out
	}

	@Test
	public void testSaveAndLoadLibrary() {
		library.addBook(1, "Book One", "Author A", "Fiction");
		library.addBook(2, "Book Two", "Author B", "Non-Fiction");

		library.saveToFile("src/main/resources/testfiles/library_test.txt");
		Library newLibrary = new Library();
		newLibrary.loadFromFile("src/main/resources/testfiles/library_test.txt");

		Assert.assertEquals(2, newLibrary.searchByTitle("Book").size());
	}
}
