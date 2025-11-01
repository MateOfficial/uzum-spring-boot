package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class StatisticsServiceTest {
    @InjectMocks
    StatisticsService statisticsService;
    @Mock
    PersonRepository personRepository;
    @Mock
    CarRepository carRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void stats_CountVendors() {
        Car c1 = new Car(); c1.setModel("BMW-X5");
        Car c2 = new Car(); c2.setModel("Toyota-Camry");
        Car c3 = new Car(); c3.setModel("BMW-M3");
        when(carRepository.findAll()).thenReturn(Arrays.asList(c1,c2,c3));
        when(personRepository.count()).thenReturn(2L);
        when(carRepository.count()).thenReturn(3L);
        StatisticsResponse resp = statisticsService.getStatistics();
        assertEquals(2, resp.getUniquevendorcount());
        assertEquals(2, resp.getPersoncount());
        assertEquals(3, resp.getCarcount());
    }
}
