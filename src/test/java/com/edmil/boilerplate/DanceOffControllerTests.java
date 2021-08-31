package com.edmil.boilerplate;

import com.edmil.boilerplate.controller.DanceOffController;
import com.edmil.boilerplate.model.Battle;
import com.edmil.boilerplate.service.DanceOffService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.edmil.boilerplate.assembler.DanceOffAssembler;
import com.edmil.boilerplate.assembler.representation.DanceOffRepresentation;
import com.edmil.boilerplate.model.DanceOff;
import com.edmil.boilerplate.model.Robot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DanceOffController.class)
@AutoConfigureMockMvc(addFilters = false) // Let us test without the security module active
public class DanceOffControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    DanceOffService danceOffService;

    DanceOffAssembler assembler = new DanceOffAssembler();

    Robot robot1 = new Robot("Test Robot", "Epic Spin", 5L, false, "https://robohash.org/test");
    Robot robot2 = new Robot("Test Robot 2", "Impressive Spin", 9L, false, "https://robohash.org/test2");
    Robot robot3 = new Robot("Test Robot 3", "Gangnam Style", 2L, false, "https://robohash.org/test3");
    Robot robot4 = new Robot("Test Robot 4 ", "Suave Takeoff", 3L, false, "https://robohash.org/test4");
    Robot robot5 = new Robot("Test Robot 5", "Epic Spin", 4L, false, "https://robohash.org/test5");
    Robot robot6 = new Robot("Test Robot 6", "Inverted Manoton", 3L, false, "https://robohash.org/test6");
    Robot robot7 = new Robot("Test Robot 7", "Regular Manoton", 5L, false, "https://robohash.org/test7");
    Robot robot8 = new Robot("Test Robot 8 ", "Breakdance Glory", 11L, false, "https://robohash.org/test8");
    Robot robot9 = new Robot("Test Robot 9", "The Ultimate Showoff", 1L, false, "https://robohash.org/test9");
    Robot robot10 = new Robot("Test Robot 10", "Tiptoe Mania", 8L, false, "https://robohash.org/test10");


    Battle battle1 = new Battle(robot1, robot2, 'A');
    Battle battle2 = new Battle(robot3, robot4, 'B');
    Battle battle3 = new Battle(robot5, robot6, 'B');
    Battle battle4 = new Battle(robot7, robot8, 'A');
    Battle battle5 = new Battle(robot9, robot10, 'B');

    LocalDate date = LocalDate.now();

    DanceOff danceOff = new DanceOff(Arrays.asList(battle1,battle2,battle3,battle4,battle5), date);

    List<DanceOff> danceOffs = new ArrayList<DanceOff>(Arrays.asList(danceOff));


    @Test
    public void getAllDanceOffsEmpty() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/danceoffs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.danceOffRepresentationList").doesNotExist());
    }

    @Test
    public void getAllDanceOffs() throws Exception {

        List<EntityModel<DanceOffRepresentation>> representation = new ArrayList<>();

        for(DanceOff danceOff : danceOffs){
            representation.add(assembler.toModel(danceOff));
        }

        Mockito.when(danceOffService.allDanceOffs()).thenReturn(assembler.danceOffList(representation));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/danceoffs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.danceOffRepresentationList", hasSize(1)));
    }

    @Test
    public void createDanceOff_success() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/danceoffs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(danceOff).getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().isOk());
    }

    @Test
    public void createDanceOff_failure_missing_date() throws Exception {

        danceOff.setDate(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/danceoffs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(danceOff).getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    public void createDanceOff_failure_missing_winner() throws Exception {

        danceOff.getBattles().get(0).setWinner(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/danceoffs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(danceOff).getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    public void createDanceOff_failure_battle_size_not_5() throws Exception {

        DanceOff fourBattlesDanceOff = new DanceOff(Arrays.asList(battle1,battle2,battle3,battle4), date);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/danceoffs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(fourBattlesDanceOff).getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

}
