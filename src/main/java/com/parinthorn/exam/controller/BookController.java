package com.parinthorn.exam.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parinthorn.exam.entity.Book;
import com.parinthorn.exam.exception.ResourceNotFoundException;
import com.parinthorn.exam.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /*
     * @GetMapping
     * public List<Book> getAllBooks() {
     * return bookService.getAllBooks();
     * }
     */

    @GetMapping
    public Object getAllBooks(@RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer perPage) {
        return bookService.getBooksByPage(pageNumber, perPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            throw new ResourceNotFoundException("Book not found with id " + id);
        }
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            Book updatedBook = book.get();
            updatedBook.setTitle(bookDetails.getTitle());
            updatedBook.setAuthor(bookDetails.getAuthor());
            updatedBook.setIsbn(bookDetails.getIsbn());
            updatedBook.setPublishedDate(bookDetails.getPublishedDate());
            bookService.saveBook(updatedBook);
            return ResponseEntity.ok(updatedBook);
        } else {
            throw new ResourceNotFoundException("Book not found with id " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookService.getBookById(id).isPresent()) {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException("Book not found with id " + id);
        }
    }
}