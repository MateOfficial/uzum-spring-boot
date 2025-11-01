package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // Сохраняем нового человека, если данные валидны, иначе кидаем ошибку
    public void savePerson(PersonRequest dto) {
        LocalDate birthdate;
        try {
            birthdate = LocalDate.parse(dto.getBirthdate(), formatter);
        } catch (Exception e) {
            throw new RuntimeException("Birthdate format is wrong, must be dd.MM.yyyy");
        }
        if (birthdate.isAfter(LocalDate.now())) {
            throw new RuntimeException("Birthdate can't be in future");
        }
        int age = Period.between(birthdate, LocalDate.now()).getYears();
        if (age < 18) {
            throw new RuntimeException("Person must be 18 or older");
        }
        if (personRepository.existsById(dto.getId())) {
            throw new RuntimeException("Person with this id already exists");
        }
        Person p = new Person();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setBirthdate(birthdate);
        personRepository.save(p);
    }

    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }
}
