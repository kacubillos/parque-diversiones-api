package com.pixels.parquediversiones.domain;

import com.pixels.parquediversiones.persistence.entity.Horario;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Game {
    private Integer gameId;
    private String name;
    private Integer capacityPersons;
    private LocalTime duration;
    private Double salesPrice;
    private LocalDateTime registrationDate;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacityPersons() {
        return capacityPersons;
    }

    public void setCapacityPersons(Integer capacityPersons) {
        this.capacityPersons = capacityPersons;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
