package com.example.authorize.bookbucket.model.entity;


import com.example.authorize.bookbucket.model.entity.TitleEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "book")
@Getter @Setter
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @SequenceGenerator( name = "book_sequence", sequenceName = "book_id_seq", allocationSize = 1 , initialValue = 10)
    private int id;
    private LocalDate datePublication;
    private String publisher;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="title_fk")
    private TitleEntity title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return id == that.id && Objects.equals(datePublication, that.datePublication) && Objects.equals(publisher, that.publisher) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datePublication, publisher, price);
    }
}