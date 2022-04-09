package com.fedorovigor.bookstore.userbucket.mapper;

import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;
import com.fedorovigor.bookstore.userbucket.model.dto.BookFullResponse;

import java.util.List;

public interface UserBucketMapper {

    List<BookFullResponse> createUserBookResponse(List<BookDTO> bookDto, List<TitleResponse> titles);
    BookFullResponse createFullBookResponse(BookDTO book, TitleResponse title);

}
