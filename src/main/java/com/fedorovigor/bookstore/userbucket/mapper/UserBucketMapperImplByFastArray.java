//package com.fedorovigor.bookstore.userbucket.mapper;
//
//import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
//import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;
//import com.fedorovigor.bookstore.userbucket.model.dto.BookFullResponse;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserBucketMapperImplByFastArray extends UserBucketAbstractMapper{
//    @Override
//    public List<BookFullResponse> createUserBookResponse(List<BookDTO> bookDto, List<TitleResponse> titles) {
//        List<BookFullResponse> fullBooks = new ArrayList<>();
//
//        titles.sort( (t1,t2) -> t2.getId() - t1.getId());
//
//        for (var b : bookDto) {
//            BookFullResponse fullBook = this.createFullBookResponse(b, titles.);
//            fullBooks.add()
//        }
//
//    }
//
//    private TitleResponse binarySearch()
//}
