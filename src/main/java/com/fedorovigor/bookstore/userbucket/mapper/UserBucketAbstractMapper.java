package com.fedorovigor.bookstore.userbucket.mapper;

import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;
import com.fedorovigor.bookstore.userbucket.model.dto.BookFullResponse;

import java.util.List;

public abstract class UserBucketAbstractMapper implements UserBucketMapper {

    public abstract List<BookFullResponse> createUserBookResponse(List<BookDTO> bookDto, List<TitleResponse> titles);

    public BookFullResponse createFullBookResponse(BookDTO book, TitleResponse title) {
        BookFullResponse response = new BookFullResponse().builder()
                .id(book.getId())
                .authorNames(title.getAuthorsNames())
                .dateWriting(title.getDateWriting())
                .genres(title.getGenres())
                .imgUrl(title.getImgUrl())
                .title(title.getTitle())
                .datePublication(book.getDatePublication())
                .downloadUrl(book.getDownloadUrl())
                .price(book.getPrice())
                .publisher(book.getPublisher())
                .build();

        return response;
    }
}
