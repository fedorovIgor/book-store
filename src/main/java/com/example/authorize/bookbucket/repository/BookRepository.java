package com.example.authorize.bookbucket.repository;

import com.example.authorize.bookbucket.model.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
}
