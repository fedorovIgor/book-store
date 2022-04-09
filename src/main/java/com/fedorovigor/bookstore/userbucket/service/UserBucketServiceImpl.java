package com.fedorovigor.bookstore.userbucket.service;

import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;
import com.fedorovigor.bookstore.userbucket.mapper.UserBucketMapper;
import com.fedorovigor.bookstore.userbucket.model.dto.BookIdRequest;
import com.fedorovigor.bookstore.userbucket.model.dto.UserBookResponse;
import com.fedorovigor.bookstore.userbucket.model.entity.UserResourceEntity;
import com.fedorovigor.bookstore.userbucket.repository.UserBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserBucketServiceImpl implements UserBucketService {

    private final UserBookRepository userBookRepository;
    private final ConnectToBookBucket connectToBookBucket;
    private final UserBucketMapper mapper;

    @Override
    public void addBookToUser(String name, BookIdRequest bookId) {
        UserResourceEntity e = new UserResourceEntity(name, bookId.getBookId());
        userBookRepository.save(e);
    }

    @Override
    public UserBookResponse getUserBooks(String username) {
//        1. get all bookIds from UBRepo
//        2. get titleId related to book
//        4. get BookResponse by bookId
//        5. compact to full book

        var bookIds = userBookRepository.findRelatedIds(username);

        List<BookDTO> bookDto = connectToBookBucket.getBookByIds(bookIds);

        var titleIds = bookDto.stream()
                .map(b -> b.getTitle().getId())
                .collect(Collectors.toList());

        List<TitleResponse> titles = connectToBookBucket.getAllTitlesByIds(titleIds);

        var fullBooks = mapper.createUserBookResponse(bookDto, titles);

        return new UserBookResponse(fullBooks);
    }
}
