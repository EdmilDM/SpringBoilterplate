package com.edmil.boilerplate.service;

import com.edmil.boilerplate.assembler.RobotAssembler;
import com.edmil.boilerplate.exception.customexceptions.NotFoundException;
import com.edmil.boilerplate.model.Robot;
import com.edmil.boilerplate.repository.RobotRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RobotService {

    private final RobotRepository repository;

    private final RobotAssembler assembler;

    public RobotService(RobotRepository repository, RobotAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    public CollectionModel<EntityModel<Robot>> allRobots() {
        List<EntityModel<Robot>> robots = repository.findAll().stream().map(assembler::toModel)
                .collect(Collectors.toList());
        return assembler.robotList(robots);
    }

    public EntityModel<Robot> robotById(Long id) {
        Robot robot = repository.findById(id).orElseThrow(() -> new NotFoundException(Robot.class.getSimpleName(), id));
        return assembler.toModel(robot);
    }

    public EntityModel<Robot> createRobot(Robot newRobot) {
        Robot robot = repository.save(newRobot);
        return assembler.toModel(robot);
    }
}
