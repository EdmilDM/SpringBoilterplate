package com.edmil.boilerplate.service;

import com.edmil.boilerplate.assembler.DanceOffAssembler;
import com.edmil.boilerplate.assembler.representation.DanceOffRepresentation;
import com.edmil.boilerplate.model.DanceOff;
import com.edmil.boilerplate.repository.DanceOffRepository;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DanceOffService {

    private final DanceOffRepository repository;

    private final DanceOffAssembler assembler;

    public DanceOffService(DanceOffRepository repository, DanceOffAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    public EntityModel<DanceOffRepresentation> createDanceOff(DanceOff newDanceOff){
        DanceOff createdDanceOff = repository.save(newDanceOff);
        return assembler.toModel(createdDanceOff);
    }

    public CollectionModel<EntityModel<DanceOffRepresentation>> allDanceOffs() {
        List<EntityModel<DanceOffRepresentation>> danceOffs = repository.findAll(Sort.by(Sort.Direction.DESC, "date")).
                stream().map(assembler::toModel).collect(Collectors.toList());
        return assembler.danceOffList(danceOffs);
    }
}
