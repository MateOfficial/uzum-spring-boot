package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private PersonRepository personRepository;

    // Сохраняем новый автомобиль, если всё правильно, иначе ошибка
    public void saveCar(CarRequest dto) {
        if (dto.getHorsepower() == null || dto.getHorsepower() <= 0) {
            throw new RuntimeException("Horsepower must be > 0");
        }
        String model = dto.getModel();
        // Модель должна быть вида BMW-X5 (производитель-модель), без минуса в начале/конце, оба слова не пустые
        if (!Pattern.matches("^[^-]+-[^-]+$", model)) {
            throw new RuntimeException("Model must be like vendor-model, example: BMW-X5");
        }
        if (!personRepository.existsById(dto.getOwnerId())) {
            throw new RuntimeException("Owner person does not exist");
        }
        Car c = new Car();
        c.setId(dto.getId());
        c.setModel(model);
        c.setHorsepower(dto.getHorsepower());
        c.setOwnerId(dto.getOwnerId());
        carRepository.save(c);
    }

    public void deleteAll() {
        carRepository.deleteAll();
    }
}
