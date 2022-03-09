package com.example.authorize.user.model.entity;

import com.example.authorize.user.model.entity.AuthorityEntity;
import com.example.authorize.user.model.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
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


    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "role_authority",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_id") }
    )
    private Collection<AuthorityEntity> authorities;

}
