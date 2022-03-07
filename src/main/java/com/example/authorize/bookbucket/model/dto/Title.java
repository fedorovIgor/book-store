package com.example.authorize.bookbucket.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Title {

    private String name;
    private LocalDate dateWriting;

}
