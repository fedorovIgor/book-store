package com.fedorovigor.bookstore.user.security;

import com.fedorovigor.bookstore.user.controller.UserController;
import com.fedorovigor.bookstore.user.model.dto.UserRequest;
import com.fedorovigor.bookstore.user.service.JpaUserDetailsManager;
import com.fedorovigor.bookstore.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerAccessTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private JpaUserDetailsManager userDetailsService;

    ObjectMapper objectMapper;


    @BeforeEach
    public void before() {
        objectMapper = new ObjectMapper();
    }


    @Test
    @WithMockUser(username = "user", password = "pwd", authorities = {"get_users"})
    public void getUserIsCorrect() throws Exception {
        mockMvc.perform(get("/api/v1/user")).andExpect(status().isOk());
    }


    @Test
    public void whenUserNotAuthorized() throws Exception {

        UserRequest requestBody = new UserRequest();

        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(get("/api/v1/user/login")).andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/user/create")
                        .header("Access-Control-Request-Method", "OPTIONS", "GET", "HEAD", "POST", "PUT")
                        .header("Origin","http://localhost:4200")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf().asHeader()))
                .andExpect(status().isOk());

        mockMvc.perform(put("/api/v1/user/password")
                        .header("Access-Control-Request-Method", "PUT")
                        .header("Origin", "http://localhost:4200")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf().asHeader()))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", authorities = {})
    public void whenUserAuthorized_NoPermissions() throws Exception {

        UserRequest requestBody = new UserRequest();
        requestBody.setOldPassword("123");
        requestBody.setNewPassword("1234");

        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(get("/api/v1/user/login"))
                .andExpect(status().isOk());

        mockMvc.perform(put("/api/v1/user/password")
                        .header("Access-Control-Request-Method", "PUT")
                        .header("Origin", "http://localhost:4200")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf().asHeader()))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/user/create")
                        .header("Access-Control-Request-Method", "POST")
                        .header("Origin", "http://localhost:4200")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf().asHeader()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isForbidden());
    }

}
