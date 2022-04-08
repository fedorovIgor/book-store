package com.fedorovigor.bookstore.bookbucket.repository;

import com.fedorovigor.bookstore.bookbucket.model.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
}
