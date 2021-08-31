package com.edmil.boilerplate;

import static org.assertj.core.api.Assertions.assertThat;

import com.edmil.boilerplate.controller.DanceOffController;
import com.edmil.boilerplate.controller.RobotController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ControllersSanityTests {

    @Autowired
    private RobotController robotController;

    @Autowired
    private DanceOffController danceOffController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(robotController).isNotNull();
        assertThat(danceOffController).isNotNull();
    }

}
