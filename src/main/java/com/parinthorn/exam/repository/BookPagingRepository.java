package com.parinthorn.exam.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.parinthorn.exam.entity.Book;

@Repository
public interface BookPagingRepository extends PagingAndSortingRepository<Book, Long> {
}