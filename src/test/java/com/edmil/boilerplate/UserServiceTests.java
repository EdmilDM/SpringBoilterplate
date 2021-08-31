package com.edmil.boilerplate;

import com.edmil.boilerplate.exception.customexceptions.AuthenticationFailedException;
import com.edmil.boilerplate.model.User;
import com.edmil.boilerplate.repository.UserRepository;
import com.edmil.boilerplate.service.UserService;
import com.edmil.boilerplate.exception.customexceptions.UserAlreadyExists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        String password = new BCryptPasswordEncoder().encode("password");
        user1 = new User("admin", password);
        user2 = new User("sudo", password);
    }

    @AfterEach
    public void tearDown() {
        user1 = null;
    }

    @Test
    public void loginUser_failure_doesnt_exist() {
        Mockito.when(userService.getUser(Mockito.anyString())).thenReturn(null);
        assertThrows(AuthenticationFailedException.class, () -> userService.login(user1.getUsername(), user1.getPassword()));
    }

    @Test
    public void loginUser_success() {
        Mockito.when(userService.getUser(Mockito.anyString())).thenReturn(user1);
        User user = userService.login("admin", "password");
        assertEquals("admin", user.getUsername());
        assertEquals(null, user.getPassword());
    }

    @Test
    public void createToken() {
        String token = userService.getJWTToken("test");
        Assertions.assertTrue(token.contains("Token "));
    }

    @Test
    public void createUser_success() throws UserAlreadyExists {
        Mockito.when(userRepository.save(user2)).thenReturn(user2);
        User user = userService.signup(user2);
        Assertions.assertTrue(user.getPassword() == null);
        Assertions.assertTrue(user.getToken().contains("Token "));
    }

}
