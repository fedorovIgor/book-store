package com.fedorovigor.bookstore.bookbucket.model.dto;

import com.fedorovigor.bookstore.bookbucket.model.entity.TitleEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {
    private int id;
    private LocalDate datePublication;
    private String publisher;
    private Integer price;
    private String downloadUrl;
    private TitleEntity title;

    public Builder builder() {
        return new Builder();
    }

    public class Builder {
        private final BookDTO bookDTO = new BookDTO();

        public Builder id(int id) {
            bookDTO.setId(id);
            return this;
        }
        public Builder datePublication(LocalDate date) {
            bookDTO.setDatePublication(date);
            return this;
        }
        public Builder publisher(String publisher) {
            bookDTO.setPublisher(publisher);
            return this;
        }
        public Builder price(int price) {
            bookDTO.setPrice(price);
            return this;
        }
        public Builder downloadUrl(String url) {
            bookDTO.setDownloadUrl(url);
            return this;
        }
        public Builder title(TitleEntity e) {
            bookDTO.setTitle(e);
            return this;
        }

        public BookDTO build() {
            return bookDTO;
        }
    }
}
