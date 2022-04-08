package com.fedorovigor.bookstore.bookbucket.service;

import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
import com.fedorovigor.bookstore.bookbucket.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private  final BucketMapping mapper;

    public List<BookDTO> getAllBooksByIds(List<Integer> bookIds) {
        var entities = bookRepository.findAllInId(bookIds);
        var result = entities.stream()
                .map(mapper::bookEntityToBookDTO)
                .collect(Collectors.toList());

        return result;
    }
}
