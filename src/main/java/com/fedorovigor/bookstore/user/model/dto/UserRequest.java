package com.fedorovigor.bookstore.user.model.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String role;
    private String oldPassword;
    private String newPassword;
}
