package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class CarServiceTest {
    @InjectMocks
    CarService carService;
    @Mock
    CarRepository carRepository;
    @Mock
    PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCar_Ok() {
        CarRequest req = new CarRequest();
        req.setId(1L);
        req.setModel("BMW-X5");
        req.setHorsepower(250);
        req.setOwnerId(7L);
        when(personRepository.existsById(7L)).thenReturn(true);
        carService.saveCar(req);
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void saveCar_BadHorsepower() {
        CarRequest req = new CarRequest();
        req.setId(2L);
        req.setModel("BMW-X5");
        req.setHorsepower(0);
        req.setOwnerId(7L);
        RuntimeException ex = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> carService.saveCar(req));
        assert(ex.getMessage().contains("Horsepower"));
    }

    @Test
    void saveCar_BadModel() {
        CarRequest req = new CarRequest();
        req.setId(3L);
        req.setModel("BADMODEL");
        req.setHorsepower(100);
        req.setOwnerId(7L);
        when(personRepository.existsById(7L)).thenReturn(true);
        RuntimeException ex = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> carService.saveCar(req));
        assert(ex.getMessage().contains("vendor-model"));
    }

    @Test
    void saveCar_OwnerNotExist() {
        CarRequest req = new CarRequest();
        req.setId(5L);
        req.setModel("BMW-X5");
        req.setHorsepower(100);
        req.setOwnerId(100L);
        when(personRepository.existsById(100L)).thenReturn(false);
        RuntimeException ex = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> carService.saveCar(req));
        assert(ex.getMessage().contains("Owner person does not exist"));
    }
}
