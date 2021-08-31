package com.edmil.boilerplate.controller;

import com.edmil.boilerplate.service.RobotService;
import com.edmil.boilerplate.model.Robot;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RobotController {

    private final RobotService service;

    public RobotController(RobotService service) {
        this.service = service;
    }

    @GetMapping("/robots")
    public CollectionModel<EntityModel<Robot>> allRobots() {
        return service.allRobots();
    }

    @GetMapping("/robots/{id}")
    public EntityModel<Robot> robotById(@PathVariable Long id) {
        return service.robotById(id);
    }

    @PostMapping("/robots")
    public EntityModel<Robot> createRobot(@Valid @RequestBody Robot newRobot) {
        return service.createRobot(newRobot);
    }
}
