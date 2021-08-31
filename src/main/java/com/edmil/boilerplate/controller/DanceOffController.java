package com.edmil.boilerplate.controller;

import com.edmil.boilerplate.assembler.representation.DanceOffRepresentation;
import com.edmil.boilerplate.model.DanceOff;
import com.edmil.boilerplate.service.DanceOffService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class DanceOffController {

    private final DanceOffService service;

    public DanceOffController(DanceOffService service) {
        this.service = service;
    }

    @PostMapping("/danceoffs")
    public EntityModel<DanceOffRepresentation> createDanceOff(@Valid @RequestBody DanceOff newDanceOff) {
        return service.createDanceOff(newDanceOff);
    }

    @GetMapping("/danceoffs")
    public CollectionModel<EntityModel<DanceOffRepresentation>> allDanceOffs() {
        return service.allDanceOffs();
    }

}
