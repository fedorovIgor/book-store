package com.fedorovigor.bookstore.bookbucket.model.dto;

import lombok.Data;

@Data
public class GenreResponse implements ResponsePageable {

    private int id;
    private String genre;

    public Builder builder() {
        return new Builder();
    }

    public class Builder{
        private final GenreResponse g = new GenreResponse();

        public Builder id(int i) {
            g.setId(i);
            return this;
        }

        public Builder genre(String s) {
            g.setGenre(s);
            return this;
        }

        public GenreResponse build() {
            return g;
        }
    }
}
