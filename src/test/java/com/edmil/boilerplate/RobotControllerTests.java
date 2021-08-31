package com.edmil.boilerplate;

import com.edmil.boilerplate.assembler.RobotAssembler;
import com.edmil.boilerplate.service.RobotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.edmil.boilerplate.controller.RobotController;
import com.edmil.boilerplate.exception.customexceptions.NotFoundException;
import com.edmil.boilerplate.model.Robot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RobotController.class)
public class RobotControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    RobotService robotService;

    RobotAssembler assembler = new RobotAssembler();

    // Mocked robots
    Robot robot1 = new Robot("Test Robot", "Epic Spin", 5L, false, "https://robohash.org/test");
    Robot robot2 = new Robot("Test Robot 2 ", "Impressive Spin", 9L, false, "https://robohash.org/test2");

    @Test
    public void getAllRobotsEmpty() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/robots")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.robotList").doesNotExist());
    }

    @Test
    public void getAllRobotsNotEmpty() throws Exception {
        List<Robot> robotList = new ArrayList<>(Arrays.asList(robot1,robot2));
        Mockito.when(robotService.allRobots()).thenReturn(assembler.robotList(robotList.stream().map(assembler::toModel).collect(Collectors.toList())));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/robots")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.robotList", hasSize(2)));
    }

    @Test
    public void getRobotById_success() throws Exception {

        Mockito.when(robotService.robotById(1L)).thenReturn(assembler.toModel(robot1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/robots/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Test Robot")));
    }

    @Test
    public void getRobotById_failure_doesntExist() throws Exception {

        Mockito.when(robotService.robotById(5L)).thenThrow(new NotFoundException(Robot.class.getSimpleName(), 5L));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/robots/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    public void createRobot_success() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/robots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(robot1).getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().isOk());
    }

    @Test
    public void createRobot_failure_null_name() throws Exception {

        robot1.setName(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/robots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(robot1).getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

}
