package com.fedorovigor.bookstore.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Authority implements Serializable {
    private String name;
    private String description;

    public Authority(String name) {
        this.name = name;
    }
}
