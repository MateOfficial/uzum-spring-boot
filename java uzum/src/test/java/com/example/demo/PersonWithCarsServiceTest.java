package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PersonWithCarsServiceTest {
    @InjectMocks
    PersonWithCarsService service;
    @Mock
    PersonRepository personRepository;
    @Mock
    CarRepository carRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void personWithCars_Ok() {
        Person p = new Person();
        p.setId(5L);
        p.setName("Alex");
        p.setBirthdate(LocalDate.of(2000,1,1));
        Car c1 = new Car(); c1.setId(1L); c1.setModel("BMW-X5"); c1.setHorsepower(200); c1.setOwnerId(5L);
        Car c2 = new Car(); c2.setId(2L); c2.setModel("Toyota-Camry"); c2.setHorsepower(170); c2.setOwnerId(5L);
        when(personRepository.findById(5L)).thenReturn(Optional.of(p));
        when(carRepository.findAllByOwnerId(5L)).thenReturn(Arrays.asList(c1,c2));
        PersonWithCarsResponse res = service.getPersonWithCars(5L);
        assertEquals(2, res.getCars().size());
        assertEquals("Alex", res.getName());
        assertEquals("BMW-X5", res.getCars().get(0).getModel());
    }
}
