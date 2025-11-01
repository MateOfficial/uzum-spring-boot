package com.example.demo;

import jakarta.validation.constraints.*;

// DTO для создания нового автомобиля (данные которые приходят из запроса)
public class CarRequest {
    @NotNull(message = "id is required")
    private Long id;
    @NotBlank(message = "model is required. Format should be vendor-model for example: BMW-X5. No '-' at first/last and no empty vendor/model")
    private String model;
    @NotNull(message = "horsepower is required")
    @Min(value = 1, message = "horsepower must be > 0")
    private Integer horsepower;
    @NotNull(message = "ownerId required")
    private Long ownerId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Integer getHorsepower() { return horsepower; }
    public void setHorsepower(Integer horsepower) { this.horsepower = horsepower; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
}
