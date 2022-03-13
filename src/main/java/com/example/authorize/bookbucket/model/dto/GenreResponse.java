package com.example.authorize.bookbucket.model.dto;

import com.example.authorize.bookbucket.model.entity.GenreEntity;
import lombok.Data;

@Data
public class GenreResponse implements ResponsePageable {
    String genre;

    public GenreResponse(GenreEntity e) {
        this.genre = e.getGenreName();
    }
}
