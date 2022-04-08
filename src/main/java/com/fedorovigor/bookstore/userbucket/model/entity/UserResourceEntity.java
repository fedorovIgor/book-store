package com.fedorovigor.bookstore.userbucket.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_resource")
@Getter @Setter
public class UserResourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_resource")
    @SequenceGenerator(name = "user_resource_sequence", sequenceName = "user_resource_seq", allocationSize = 1, initialValue = 10)
    private int id;
    private String username;
    private int bookId;

    public UserResourceEntity(String username, int book_id) {
        this.username = username;
        this.bookId = bookId;
    }

    public UserResourceEntity() {
    }
}
