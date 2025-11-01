package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

// Репозиторий для людей (extends JpaRepository)
public interface PersonRepository extends JpaRepository<Person, Long> {
    // Тут ничего лишнего — используем стандартные методы JPA
}
