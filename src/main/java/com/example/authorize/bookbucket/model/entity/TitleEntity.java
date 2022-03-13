package com.example.authorize.bookbucket.model.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "title")
@Getter @Setter
public class TitleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "title_sequence")
    @SequenceGenerator( name = "title_sequence", sequenceName = "title_id_seq", allocationSize = 1 , initialValue = 10)
    private int id;
    private String name;
    private LocalDate dateWriting;
    private String imageUrl;

    @ManyToMany(mappedBy = "titles")
    private Set<AuthorEntity> authors;

    @ManyToMany(mappedBy = "titles")
    private Set<GenreEntity> genres;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY)
    private Set<BookEntity> booksEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitleEntity that = (TitleEntity) o;
        return id == that.id && name.equals(that.name) && Objects.equals(dateWriting, that.dateWriting) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateWriting, imageUrl);
    }
}
