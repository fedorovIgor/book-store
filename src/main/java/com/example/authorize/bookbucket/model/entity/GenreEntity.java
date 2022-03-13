package com.example.authorize.bookbucket.model.entity;

import com.example.authorize.bookbucket.model.entity.TitleEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
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
    private List<TitleEntity> titles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreEntity that = (GenreEntity) o;
        return id == that.id && genreName.equals(that.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genreName);
    }
}
