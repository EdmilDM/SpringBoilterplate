package com.edmil.boilerplate.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
public class DanceOff {
    private @Id @GeneratedValue Long id;
    @ElementCollection
    @NotNull(message="Battles can not be null")
    @Size(min = 5, max = 5, message="Battles must be of size 5")
    @Valid
    private List<Battle> battles;
    @NotNull(message="Date can not be null")
    private LocalDate date;

    public DanceOff(){

    }

    public DanceOff(List<Battle> battles, LocalDate date) {
        this.battles = battles;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Battle> getBattles() {
        return battles;
    }

    public void setBattles(List<Battle> battles) {
        this.battles = battles;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
