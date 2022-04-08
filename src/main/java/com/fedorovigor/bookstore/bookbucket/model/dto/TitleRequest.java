package com.fedorovigor.bookstore.bookbucket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class TitleRequest {

    private Integer titleId;
    private String titleName;
    private LocalDate dataWrite;
    private Set<GenreResponse> genre;
    private String imgUrl;

}
