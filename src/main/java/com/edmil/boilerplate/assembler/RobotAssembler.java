package com.edmil.boilerplate.assembler;

import com.edmil.boilerplate.controller.RobotController;
import com.edmil.boilerplate.model.Robot;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RobotAssembler implements RepresentationModelAssembler<Robot, EntityModel<Robot>> {

    @Override
    public EntityModel<Robot> toModel(Robot robot) {

        return EntityModel.of(robot,
                linkTo(methodOn(RobotController.class).robotById(robot.getId())).withSelfRel(),
                linkTo(methodOn(RobotController.class).allRobots()).withRel("robots"));
    }

    public CollectionModel<EntityModel<Robot>> robotList(List<EntityModel<Robot>> robots){
        return CollectionModel.of(robots, linkTo(methodOn(RobotController.class).allRobots()).withSelfRel());
    }
}
