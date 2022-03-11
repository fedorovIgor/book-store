package com.example.authorize.bookbucket.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TitleResponse implements ResponseData {
    private String title;
    private LocalDate dateWriting;
    private String imgUrl;
    private String authorsNames;
    private String genres;
}
