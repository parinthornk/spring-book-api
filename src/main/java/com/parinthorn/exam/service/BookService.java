package com.parinthorn.exam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.parinthorn.exam.entity.Book;
import com.parinthorn.exam.repository.BookPagingRepository;
import com.parinthorn.exam.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookPagingRepository bookPagingRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Page<Book> getBooksByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return (Page<Book>) bookPagingRepository.findAll(pageable);
    }
}
