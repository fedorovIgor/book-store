package com.fedorovigor.bookstore.bookbucket.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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

    public void addBook(BookEntity e) {
        booksEntity.add(e);
    }

    public void removeBook(BookEntity e) {
        booksEntity.remove(e);
    }

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
