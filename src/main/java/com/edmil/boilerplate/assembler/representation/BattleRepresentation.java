package com.edmil.boilerplate.assembler.representation;

import com.edmil.boilerplate.model.Robot;
import org.springframework.hateoas.EntityModel;

public class BattleRepresentation {

    private EntityModel<Robot> robotA;
    private EntityModel<Robot> robotB;
    private Character winner;

    public EntityModel<Robot> getRobotA() {
        return robotA;
    }

    public void setRobotA(EntityModel<Robot> robotA) {
        this.robotA = robotA;
    }

    public EntityModel<Robot> getRobotB() {
        return robotB;
    }

    public void setRobotB(EntityModel<Robot> robotB) {
        this.robotB = robotB;
    }

    public Character getWinner() {
        return winner;
    }

    public void setWinner(Character winner) {
        this.winner = winner;
    }
}
