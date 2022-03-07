package com.example.authorize.bookbucket.model.entity;

import com.example.authorize.bookbucket.model.entity.TitleEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "genre")
@Getter @Setter
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_sequence")
    @SequenceGenerator( name = "genre_sequence", sequenceName = "genre_id_seq", allocationSize = 1 , initialValue = 10)
    private int id;
    private String genreName;

    @ManyToMany
    @JoinTable(
            name = "title_genre",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "title_id")
    )
    private Set<TitleEntity> titles;
}
