package com.parinthorn.exam;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.parinthorn.exam.controller.BookController;
import com.parinthorn.exam.repository.BookRepository;
import com.parinthorn.exam.service.BookService;

@SpringBootTest
class ExamApplicationTests {

	@Autowired
	private BookController bookController;

	@Autowired
	private BookService bookService;

	@Autowired
	private BookRepository bookRepository;

	@Test
	void contextLoads() {
		assertNotNull(bookRepository);
		assertNotNull(bookService);
		assertNotNull(bookController);
	}

}
