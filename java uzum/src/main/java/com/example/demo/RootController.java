package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class RootController {
    // Эндпоинт /health — корень отдаёт статический UI
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("API is up. Use /statistics, /person, /car, /personwithcars");
    }
}
