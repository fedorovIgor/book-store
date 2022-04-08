package com.fedorovigor.bookstore.userbucket.mapper;

import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;
import com.fedorovigor.bookstore.userbucket.model.dto.BookFullResponse;
import com.fedorovigor.bookstore.userbucket.model.dto.UserBookResponse;
import com.fedorovigor.bookstore.userbucket.model.entity.UserResourceEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBucketMapper {

    public List<BookFullResponse> createUserBookResponse(List<BookDTO> bookDto, List<TitleResponse> titles) {
        List<BookFullResponse> fullBooks = new ArrayList<>();

        for (var b : bookDto) {
            for (var t : titles)
                if (b.getTitle().getId() == t.getId())
                    fullBooks.add(createFullBookResponse(b, t));
        }

        return fullBooks;
    }

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
