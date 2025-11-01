package com.example.demo;

import jakarta.persistence.*;

// Сущность для хранения данных об автомобиле
@Entity
public class Car {
    @Id
    private Long id;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private Integer horsepower;
    @Column(nullable = false)
    private Long ownerId;
    public Car() { }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Integer getHorsepower() { return horsepower; }
    public void setHorsepower(Integer horsepower) { this.horsepower = horsepower; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
}
