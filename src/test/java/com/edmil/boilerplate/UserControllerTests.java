package com.edmil.boilerplate;

import com.edmil.boilerplate.exception.customexceptions.AuthenticationFailedException;
import com.edmil.boilerplate.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.edmil.boilerplate.controller.UserController;
import com.edmil.boilerplate.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserService userService;

    @Test
    public void loginUser_failure() throws Exception {

        User user = new User("test", "test");
        Mockito.when(userService.login(Mockito.anyString(), Mockito.anyString())).thenThrow(new AuthenticationFailedException());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user).getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void loginUser_success() throws Exception {

        User user = new User("admin", "password");
        Mockito.when(userService.login(Mockito.anyString(), Mockito.anyString())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user).getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().isOk());
    }
}
