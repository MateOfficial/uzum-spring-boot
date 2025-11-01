package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {
    @Autowired
    private PersonService personService;
    @Autowired
    private CarService carService;
    @Autowired
    private PersonWithCarsService personWithCarsService;
    @Autowired
    private StatisticsService statisticsService;

    // Эндпоинт для добавления нового человека
    @PostMapping("/person")
    public ResponseEntity<?> addPerson(@Valid @RequestBody PersonRequest req) {
        try {
            personService.savePerson(req);
            return ResponseEntity.ok().build(); // Возвращаем 200 OK (без тела)
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error(e.getMessage()));
        }
    }
    // Эндпоинт для добавления нового автомобиля
    @PostMapping("/car")
    public ResponseEntity<?> addCar(@Valid @RequestBody CarRequest req) {
        try {
            carService.saveCar(req);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error(e.getMessage()));
        }
    }
    // Получить информацию о человеке со всеми его машинами
    @GetMapping("/personwithcars")
    public ResponseEntity<?> personWithCars(@RequestParam Long personid) {
        try {
            return ResponseEntity.ok(personWithCarsService.getPersonWithCars(personid));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error(e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error(e.getMessage()));
        }
    }
    // Получить статистику по всем данным
    @GetMapping("/statistics")
    public StatisticsResponse statistics() {
        return statisticsService.getStatistics();
    }
    // Очистить всю базу данных (удалить всех людей и машины)
    @GetMapping("/clear")
    public ResponseEntity<?> clearAll() {
        personService.deleteAll();
        carService.deleteAll();
        return ResponseEntity.ok().build();
    }

    // Вспомогательный метод — возвращает ошибку в JSON
    private Map<String, String> error(String msg) {
        Map<String, String> map = new HashMap<>();
        map.put("error", msg);
        return map;
    }
}
