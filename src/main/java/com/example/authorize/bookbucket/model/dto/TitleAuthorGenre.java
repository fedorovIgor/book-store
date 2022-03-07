package com.example.authorize.bookbucket.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class TitleAuthorGenre {

    private String name;
    private LocalDate dateWriting;
    private String ImageUrl;
    private String authorsNames;
    private String genres;
}
