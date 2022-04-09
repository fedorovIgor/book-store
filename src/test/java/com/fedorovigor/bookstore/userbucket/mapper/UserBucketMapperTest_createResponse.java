package com.fedorovigor.bookstore.userbucket.mapper;

import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;
import com.fedorovigor.bookstore.bookbucket.model.entity.TitleEntity;
import com.fedorovigor.bookstore.userbucket.model.dto.BookFullResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserBucketMapperTest_createResponse {

    UserBucketMapper mocked;

    private List<BookDTO> books;
    private List<TitleResponse> titles;
    private List<BookFullResponse> expected;
    private BookDTO b;
    private TitleResponse t;

    @BeforeEach
    public void init() {
        mocked = null;

        int titleEntityId = 10;
        int BookDTOId = 1;
        String img = "someUrl";

        TitleEntity entity = new TitleEntity();
        entity.setId(titleEntityId);

        b = new BookDTO().builder()
                .id(BookDTOId)
                .title(entity)
                .build();

        t = new TitleResponse().builder()
                .id(titleEntityId)
                .imgUrl(img)
                .build();

        BookFullResponse bf = new BookFullResponse().builder()
                .id(BookDTOId)
                .imgUrl(img)
                .build();

        titles = List.of(t);
        books = List.of(b);

        expected = List.of(bf);
    }

    @Test
    public void shouldReturnList_whenImplArray() {

        mocked = new UserBucketMapperImplByArray();

        var result = mocked.createUserBookResponse(books, titles);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void shouldReturnList_whenImplMap() {

        mocked = new UserBucketMapperImplByMap();

        var result = mocked.createUserBookResponse(books, titles);

        Assertions.assertEquals(expected, result);
    }


}