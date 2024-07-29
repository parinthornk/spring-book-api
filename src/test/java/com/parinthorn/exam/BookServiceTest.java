package com.parinthorn.exam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.parinthorn.exam.entity.Book;
import com.parinthorn.exam.repository.BookRepository;
import com.parinthorn.exam.service.BookService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");
        book1.setIsbn("ISBN1");
        book1.setPublishedDate(LocalDate.now());

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");
        book2.setIsbn("ISBN2");
        book2.setPublishedDate(LocalDate.now());

        given(bookRepository.findAll()).willReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
        assertEquals("Book 1", books.get(0).getTitle());
    }

    @Test
    public void testGetBookById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setIsbn("ISBN123");
        book.setPublishedDate(LocalDate.now());

        given(bookRepository.findById(1L)).willReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getTitle());
    }

    @Test
    public void testGetBookByIdNotFound() {
        given(bookRepository.findById(999L)).willReturn(Optional.empty());

        Optional<Book> foundBook = bookService.getBookById(999L);

        assertFalse(foundBook.isPresent());
    }

    @Test
    public void testSaveBook() {
        Book book = new Book();
        book.setTitle("New Book");
        book.setAuthor("Author");
        book.setIsbn("ISBN123");
        book.setPublishedDate(LocalDate.now());

        given(bookRepository.save(book)).willReturn(book);

        Book savedBook = bookService.saveBook(book);

        assertNotNull(savedBook);
        assertEquals("New Book", savedBook.getTitle());
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;

        bookService.deleteBook(bookId);

        then(bookRepository).should().deleteById(bookId);
    }
}
