package com.edmil.boilerplate.database;

import com.edmil.boilerplate.repository.RobotRepository;
import com.edmil.boilerplate.repository.UserRepository;
import com.edmil.boilerplate.model.Robot;
import com.edmil.boilerplate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DatabaseLoader {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    @Bean
    CommandLineRunner initRobots(RobotRepository repository) {
        return args -> {
            log.info("Adding robot #1 " + repository.save(new Robot("Test Robot", "Super Tester", 5L, false, "https://robohash.org/test1")));
            log.info("Adding robot #2 " + repository.save(new Robot("Test Robot 2", "Ultimate Tester", 4L, false, "https://robohash.org/test2")));
        };
    }

    @Bean
    CommandLineRunner initUser(UserRepository repository) {
        return args -> {
            String password = new BCryptPasswordEncoder().encode("password");
            log.info("Adding user #1 " + repository.save(new User("admin", password)));
        };
    }
}
