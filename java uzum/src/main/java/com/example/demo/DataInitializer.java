package com.example.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * При запуске добавляет пару демо-записей, если база пуста.
 * Это нужно, чтобы быстро проверить приложение без ручного ввода данных.
 */
@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private CarService carService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long persons = personRepository.count();
        if (persons == 0) {
            // Создаём демо-человека и машину через сервисы, чтобы соблюсти все правила валидации
            PersonRequest p = new PersonRequest();
            p.setId(1L);
            p.setName("Demo Person");
            p.setBirthdate("01.01.1990");
            try {
                personService.savePerson(p);
            } catch (Exception e) {
                // Минимум шума при старте, но полезно для отладки
                System.out.println("DataInitializer: failed to create demo person: " + e.getMessage());
            }

            CarRequest c = new CarRequest();
            c.setId(10L);
            c.setModel("BMW-X5");
            c.setHorsepower(200);
            c.setOwnerId(1L);
            try {
                carService.saveCar(c);
            } catch (Exception e) {
                System.out.println("DataInitializer: failed to create demo car: " + e.getMessage());
            }
        }
    }
}
