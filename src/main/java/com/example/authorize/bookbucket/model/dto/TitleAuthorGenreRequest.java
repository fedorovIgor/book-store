package com.example.authorize.bookbucket.model.dto;

import java.time.LocalDate;
import java.util.List;

public class TitleAuthorGenreRequest {

    private String name;
    private LocalDate dateWriting;
    private String ImageUrl;
    private List<Author> authors;
    private List<Genre> genres;

}
