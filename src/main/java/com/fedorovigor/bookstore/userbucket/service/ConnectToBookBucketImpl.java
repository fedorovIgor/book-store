package com.fedorovigor.bookstore.userbucket.service;

import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;
import com.fedorovigor.bookstore.bookbucket.service.BookService;
import com.fedorovigor.bookstore.bookbucket.service.TitleCommunication;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConnectToBookBucketImpl implements ConnectToBookBucket {

    private final BookService bookService;
    private final TitleCommunication titleCommunication;

    @Override
    public List<BookDTO> getBookByIds(List<Integer> bookIds) {
        return bookService.getAllBooksByIds(bookIds);
    }

    @Override
    public List<TitleResponse> getAllTitlesByIds(List<Integer> titleIds) {
        return titleCommunication.getAllTitleWithAuthorGenresByIds(titleIds);
    }
}
