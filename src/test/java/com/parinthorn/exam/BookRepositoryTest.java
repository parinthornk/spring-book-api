package com.parinthorn.exam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.parinthorn.exam.entity.Book;
import com.parinthorn.exam.repository.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveAndFindById() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setIsbn("ISBN123");
        book.setPublishedDate(LocalDate.now());

        Book savedBook = bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getTitle());
    }

    @Test
    public void testFindAll() {
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

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
    }

    @Test
    public void testUpdate() {
        Book book = new Book();
        book.setTitle("Original Title");
        book.setAuthor("Author");
        book.setIsbn("ISBN123");
        book.setPublishedDate(LocalDate.now());

        Book savedBook = bookRepository.save(book);

        savedBook.setTitle("Updated Title");
        Book updatedBook = bookRepository.save(savedBook);

        assertEquals("Updated Title", updatedBook.getTitle());
    }

    @Test
    public void testDeleteById() {
        Book book = new Book();
        book.setTitle("Book to Delete");
        book.setAuthor("Author");
        book.setIsbn("ISBN123");
        book.setPublishedDate(LocalDate.now());

        Book savedBook = bookRepository.save(book);
        Long bookId = savedBook.getId();

        bookRepository.deleteById(bookId);

        Optional<Book> deletedBook = bookRepository.findById(bookId);
        assertFalse(deletedBook.isPresent());
    }
}