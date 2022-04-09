package com.fedorovigor.bookstore.userbucket.model.dto;

import com.fedorovigor.bookstore.bookbucket.model.dto.BookDTO;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;
import com.fedorovigor.bookstore.bookbucket.model.entity.TitleEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookFullResponse {

    private int id;
    private String title;
    private LocalDate dateWriting;
    private String imgUrl;
    private String authorsNames;
    private String genres;

    private LocalDate datePublication;
    private String publisher;
    private Integer price;
    private String downloadUrl;

    public Builder builder() {
        return new Builder();
    }

    public class Builder {
        private final BookFullResponse t = new BookFullResponse();

        public Builder id(int id) {
            t.setId(id);
            return this;
        }

        public Builder title(String title) {
            t.setTitle(title);
            return this;
        }

        public Builder dateWriting(LocalDate date) {
            t.setDateWriting(date);
            return this;
        }

        public Builder imgUrl(String img) {
            t.setImgUrl(img);
            return this;
        }

        public Builder authorNames(String authorNames) {
            t.setAuthorsNames(authorsNames);
            return this;
        }

        public Builder genres(String g) {
            t.setGenres(g);
            return this;
        }

        public Builder datePublication(LocalDate date) {
            t.setDatePublication(date);
            return this;
        }
        public Builder publisher(String publisher) {
            t.setPublisher(publisher);
            return this;
        }
        public Builder price(Integer price) {
            t.setPrice(price);
            return this;
        }
        public Builder downloadUrl(String url) {
            t.setDownloadUrl(url);
            return this;
        }

        public BookFullResponse build() {
            return t;
        }
    }
}
