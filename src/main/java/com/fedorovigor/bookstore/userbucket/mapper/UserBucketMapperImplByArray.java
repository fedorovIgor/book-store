package com.fedorovigor.bookstore.userbucket.mapper;

import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;
import com.fedorovigor.bookstore.userbucket.model.dto.BookFullResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBucketMapperImplByArray extends UserBucketAbstractMapper{

    @Override
    public List<BookFullResponse> createUserBookResponse(List<BookDTO> bookDto, List<TitleResponse> titles) {
        List<BookFullResponse> fullBooks = new ArrayList<>();

        for (var b : bookDto) {
            for (var t : titles)
                if (b.getTitle().getId() == t.getId())
                    fullBooks.add(createFullBookResponse(b, t));
        }

        return fullBooks;
    }

}
