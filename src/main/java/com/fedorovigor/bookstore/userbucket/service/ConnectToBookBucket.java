package com.fedorovigor.bookstore.userbucket.service;

import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;

import java.util.List;

public interface ConnectToBookBucket {

    List<BookDTO> getBookByIds(List<Integer> bookIds);

    List<TitleResponse> getAllTitlesByIds(List<Integer> titleIds);

}
