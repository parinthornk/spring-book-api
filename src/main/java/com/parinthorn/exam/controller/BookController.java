package com.parinthorn.exam.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // @GetMapping
    // @Operation(summary = "List books", description = "Retrieve a list of books.")
    // @ApiResponse(responseCode = "200", description = "Successfully retrieved list
    // of books")
    // public Page<Book> getAllBooks(@RequestParam(defaultValue = "0", required =
    // false) Integer pageNumber,
    // @RequestParam(defaultValue = "3", required = false) Integer perPage) {
    // int pageNumberInt = pageNumber != null ? pageNumber : 0;
    // int perPageInt = perPage != null ? perPage : 3;
    // Page<Book> ret = bookService.getBooksByPage(pageNumberInt, perPageInt);

    // System.out.println("ret.getSize(): " + ret.getSize());

    // return ret;
    // }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID", description = "Retrieve a book by its ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the book")
    @ApiResponse(responseCode = "404", description = "Book not found")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            throw new ResourceNotFoundException("Book not found with id " + id);
        }
    }

    @PostMapping
    @Operation(summary = "Create a new book", description = "Create a new book entry in the system.")
    @ApiResponse(responseCode = "201", description = "Book successfully created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Update an existing book's information by ID.")
    @ApiResponse(responseCode = "200", description = "Book successfully updated")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
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
    @Operation(summary = "Delete a book", description = "Delete a book from the system by ID.")
    @ApiResponse(responseCode = "204", description = "Book successfully deleted")
    @ApiResponse(responseCode = "404", description = "Book not found")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookService.getBookById(id).isPresent()) {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException("Book not found with id " + id);
        }
    }
}