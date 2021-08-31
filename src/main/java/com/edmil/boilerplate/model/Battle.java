package com.edmil.boilerplate.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Embeddable
public class Battle {

    @ManyToOne
    @NotNull(message="RobotA can not be null")
    private Robot robotA;
    @ManyToOne
    @NotNull(message="RobotB can not be null")
    private Robot robotB;
    @NotNull(message="Winner can not be null")
    private Character winner;

    public Battle(){

    }

    public Battle(Robot robotA, Robot robotB, Character winner) {
        this.robotA = robotA;
        this.robotB = robotB;
        this.winner = winner;
    }

    public Robot getRobotA() {
        return robotA;
    }

    public void setRobotA(Robot robotA) {
        this.robotA = robotA;
    }

    public Robot getRobotB() {
        return robotB;
    }

    public void setRobotB(Robot robotB) {
        this.robotB = robotB;
    }

    public Character getWinner() {
        return winner;
    }

    public void setWinner(Character winner) {
        this.winner = winner;
    }
}