package com.edmil.boilerplate.assembler;

import com.edmil.boilerplate.assembler.representation.BattleRepresentation;
import com.edmil.boilerplate.controller.DanceOffController;
import com.edmil.boilerplate.model.Battle;
import com.edmil.boilerplate.model.DanceOff;
import com.edmil.boilerplate.assembler.representation.DanceOffRepresentation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DanceOffAssembler implements RepresentationModelAssembler<DanceOff, EntityModel<DanceOffRepresentation>> {

    RobotAssembler robotAssembler = new RobotAssembler();

    @Override
    public EntityModel<DanceOffRepresentation> toModel(DanceOff danceOff) {

        DanceOffRepresentation danceOffRepresentation = new DanceOffRepresentation();
        List<BattleRepresentation> battleRepresentation = new ArrayList<>();

        for(Battle battle : danceOff.getBattles()){
            BattleRepresentation tmp = new BattleRepresentation();
            tmp.setRobotA(robotAssembler.toModel(battle.getRobotA()));
            tmp.setRobotB(robotAssembler.toModel(battle.getRobotB()));
            tmp.setWinner(battle.getWinner());
            battleRepresentation.add(tmp);
        }

        danceOffRepresentation.setId(danceOff.getId());
        danceOffRepresentation.setDate(danceOff.getDate());
        danceOffRepresentation.setBattles(battleRepresentation);

        return EntityModel.of(danceOffRepresentation,
                WebMvcLinkBuilder.linkTo(methodOn(DanceOffController.class).allDanceOffs()).withSelfRel());
    }

    public CollectionModel<EntityModel<DanceOffRepresentation>> danceOffList(List<EntityModel<DanceOffRepresentation>>
                                                                                     danceOff){

        return CollectionModel.of(danceOff, linkTo(methodOn(DanceOffController.class).allDanceOffs()).withSelfRel());
    }
}
