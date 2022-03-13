package com.example.authorize.bookbucket.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageableResponse<T> {
    private long totalElements;
    private int currentPage;
    private int totalPages;
    private List<T> data;
}
