package com.example.authorize.user.model.dto;

import com.example.authorize.user.model.dto.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role implements Serializable {
    private String roleName;
    private Collection<Authority> authorities;
}
