package com.fedorovigor.bookstore.userbucket.mapper;

import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;
import com.fedorovigor.bookstore.userbucket.model.dto.BookFullResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserBucketMapperImplByMap extends UserBucketAbstractMapper {

    @Override
    public List<BookFullResponse> createUserBookResponse(List<BookDTO> bookDto, List<TitleResponse> titles) {
        List<BookFullResponse> fullBooks = new ArrayList<>();

        var bookMap = bookDto.stream()
                .collect(Collectors.toMap(
                    k -> k.getTitle().getId(),
                    m -> m
                ));

        var titleMap = titles.stream()
                .collect(Collectors.toMap(
                        TitleResponse::getId, t -> t
                ));

        for (var b : bookMap.entrySet()) {

            var key = b.getKey();

            if (titleMap.containsKey(key)) {
                fullBooks.add(createFullBookResponse(
                        b.getValue(),
                        titleMap.get(key)));
            }
        }

        return fullBooks;
    }
}
