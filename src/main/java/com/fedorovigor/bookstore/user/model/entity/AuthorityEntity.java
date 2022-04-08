package com.fedorovigor.bookstore.user.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "authority")
@NoArgsConstructor
@Getter @Setter
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_sequence")
    @SequenceGenerator( name = "authority_sequence", sequenceName = "authority_id_seq", allocationSize = 1)
    private int id;
    private String name;
    private String description;

}
