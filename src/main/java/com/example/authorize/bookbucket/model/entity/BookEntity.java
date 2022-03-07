package com.example.authorize.bookbucket.model.entity;


import com.example.authorize.bookbucket.model.entity.TitleEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

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
}