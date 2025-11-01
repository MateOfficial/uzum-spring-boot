package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class StatisticsService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CarRepository carRepository;

    public StatisticsResponse getStatistics() {
        StatisticsResponse resp = new StatisticsResponse();
        resp.setPersoncount(personRepository.count());
        resp.setCarcount(carRepository.count());
        Set<String> vendors = new HashSet<>();
        for (Car c : carRepository.findAll()) {
            String[] parts = c.getModel().split("-");
            if (parts.length > 0) {
                vendors.add(parts[0]);
            }
        }
        resp.setUniquevendorcount((long) vendors.size());
        return resp;
    }
}
