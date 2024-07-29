package com.parinthorn.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parinthorn.exam.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}