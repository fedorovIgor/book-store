package com.fedorovigor.bookstore.bookbucket.service;

import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;

import java.util.List;

public interface TitleCommunication {
    List<TitleResponse> getAllTitleWithAuthorGenresByIds(List<Integer> ids);
}
