package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonWithCarsService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CarRepository carRepository;

    // Возвращаем информацию о человеке и всех его машинах по его id
        // Возвращает человека и его машины по id
        public PersonWithCarsResponse getPersonWithCars(Long personId) {
        Person p = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        List<Car> cars = carRepository.findAllByOwnerId(personId);
        PersonWithCarsResponse resp = new PersonWithCarsResponse();
        resp.setId(p.getId());
        resp.setName(p.getName());
        resp.setBirthdate(p.getBirthdate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        List<PersonWithCarsResponse.CarInfo> carList = cars.stream().map(car -> {
            PersonWithCarsResponse.CarInfo ci = new PersonWithCarsResponse.CarInfo();
            ci.setId(car.getId());
            ci.setModel(car.getModel());
            ci.setHorsepower(car.getHorsepower());
            ci.setOwnerId(car.getOwnerId());
            return ci;
        }).collect(Collectors.toList());
        resp.setCars(carList);
        return resp;
    }
}
