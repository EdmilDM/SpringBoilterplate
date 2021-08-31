package com.edmil.boilerplate.assembler.representation;

import java.time.LocalDate;
import java.util.List;

public class DanceOffRepresentation {

    private Long id;
    private List<BattleRepresentation> battles;
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BattleRepresentation> getBattles() {
        return battles;
    }

    public void setBattles(List<BattleRepresentation> battles) {
        this.battles = battles;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
