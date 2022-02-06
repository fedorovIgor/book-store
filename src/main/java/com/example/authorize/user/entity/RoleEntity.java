package com.example.authorize.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter @Setter
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    @SequenceGenerator( name = "role_sequence", sequenceName = "role_id_seq", allocationSize = 1)
    private int id;
    private String roleName;

    @OneToMany(mappedBy = "role" , fetch = FetchType.LAZY)
    private List<UserEntity> users;

}
