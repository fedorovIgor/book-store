package com.fedorovigor.bookstore.bookbucket.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "author")
@Getter @Setter
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")
    @SequenceGenerator(name = "author_sequence", sequenceName = "author_id_seq", allocationSize = 1, initialValue = 10)
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String biography;

    @ManyToMany
    @JoinTable(
            name = "title_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "title_id")
    )
    private Set<TitleEntity> titles;


    public void addTitleEntity(TitleEntity e) {
        titles.add(e);
    }

    public void removeTitle(TitleEntity e) {
        titles.remove(e);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorEntity that = (AuthorEntity) o;
        return id == that.id && Objects.equals(firstName, that.firstName) && lastName.equals(that.lastName) && Objects.equals(birthday, that.birthday) && Objects.equals(biography, that.biography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthday, biography);
    }
}
