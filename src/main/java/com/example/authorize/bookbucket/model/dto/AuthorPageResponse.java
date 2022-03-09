package com.example.authorize.bookbucket.model.dto;

import java.util.List;

public record AuthorPageResponse(
        long totalElements,
        int currentPage,
        int totalPages,
        List<Author> authors) {
}
