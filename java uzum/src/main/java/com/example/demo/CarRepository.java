package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Репозиторий для машин (extends JpaRepository)
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByOwnerId(Long ownerId); // Найти все машины по id владельца
}
