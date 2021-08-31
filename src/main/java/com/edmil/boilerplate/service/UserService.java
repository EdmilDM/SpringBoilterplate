package com.edmil.boilerplate.service;

import com.edmil.boilerplate.repository.UserRepository;
import com.edmil.boilerplate.exception.customexceptions.AuthenticationFailedException;
import com.edmil.boilerplate.exception.customexceptions.UserAlreadyExists;
import com.edmil.boilerplate.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User login(String username, String password) {
        User user = getUser(username);
        if (user != null) {
            String encodedPassword = user.getPassword();
            if(encoder.matches(String.valueOf(password), encodedPassword)) {
                return prepareUser(user);
            }
        }

        throw new AuthenticationFailedException();
    }

    public User signup(User user) throws UserAlreadyExists {
        if(userRepository.findByUsername(user.getUsername()) == null){
            user.setPassword(encoder.encode(user.getPassword()));
            User newUser = userRepository.save(user);
            return prepareUser(newUser);
        } else {
            throw new UserAlreadyExists();
        }
    }

    public String getJWTToken(String username) {
        String key = "superSecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("boilerplate")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 400000))
                .signWith(SignatureAlgorithm.HS512,
                        key.getBytes()).compact();

        return "Token " + token;
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    public User prepareUser(User user){
        user.setPassword(null);
        user.setToken(getJWTToken(user.getUsername()));
        return user;
    }
}
