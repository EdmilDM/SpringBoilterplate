package com.edmil.boilerplate.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Robot {

    private @Id @GeneratedValue Long id;
    @NotNull(message="Name can not be null")
    private String name;
    @NotNull(message="Power move can not be null")
    private String powerMove;
    @NotNull(message="experience can not be null")
    private Long experience;
    @NotNull(message="Out of order can not be null")
    private boolean outOfOrder;
    @NotNull(message="Avatar can not be null")
    @URL(message="Avatar must be a valid URL")
    private String avatar;

    public Robot(){

    }

    public Robot(String name, String powerMove, Long experience, boolean outOfOrder, String avatar){
        this.name = name;
        this.powerMove = powerMove;
        this.experience = experience;
        this.outOfOrder = outOfOrder;
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPowerMove() {
        return powerMove;
    }

    public void setPowerMove(String powerMove) {
        this.powerMove = powerMove;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public boolean isOutOfOrder() {
        return outOfOrder;
    }

    public void setOutOfOrder(boolean outOfOrder) {
        this.outOfOrder = outOfOrder;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
