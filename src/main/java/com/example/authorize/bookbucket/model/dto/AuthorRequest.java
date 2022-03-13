package com.example.authorize.bookbucket.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AuthorRequest {

    private Integer authorId;
    private String firthName;
    private String lastNam;
    private LocalDate birthday;
    private String biography;
    private List<TitleRequest> writtenBooks;

}
