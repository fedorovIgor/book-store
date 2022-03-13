package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.model.dto.AuthorRequest;
import com.example.authorize.bookbucket.model.dto.Book;

public interface BookBucketCUDService extends TitleCUDService {

    void createAuthorTitle(AuthorRequest request);

}
