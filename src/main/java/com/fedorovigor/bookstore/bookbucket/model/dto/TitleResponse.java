package com.fedorovigor.bookstore.bookbucket.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TitleResponse implements ResponsePageable {

    private int id;
    private String title;
    private LocalDate dateWriting;
    private String imgUrl;
    private String authorsNames;
    private String genres;

    public Builder builder() {
        return new Builder();
    }

    public class Builder {
        private final TitleResponse t = new TitleResponse();

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

        public TitleResponse build() {
            return t;
        }
    }
}
