package com.example.demo;

import java.util.List;

// Ответ с информацией о человеке и списком его машин
public class PersonWithCarsResponse {
    private Long id;
    private String name;
    private String birthdate; // Дата рождения в формате дд.мм.гггг
    private List<CarInfo> cars;

    public PersonWithCarsResponse() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
    public List<CarInfo> getCars() { return cars; }
    public void setCars(List<CarInfo> cars) { this.cars = cars; }

    // Простая информация о машине внутри ответа
    public static class CarInfo {
        private Long id;
        private String model;
        private Integer horsepower;
        private Long ownerId;
        public CarInfo() {}
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public Integer getHorsepower() { return horsepower; }
        public void setHorsepower(Integer horsepower) { this.horsepower = horsepower; }
        public Long getOwnerId() { return ownerId; }
        public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    }
}
