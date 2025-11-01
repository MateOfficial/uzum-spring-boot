package com.example.demo;

import jakarta.validation.constraints.*;

// DTO для создания нового человека (данные которые приходят из запроса)
public class PersonRequest {
    @NotNull(message = "id is required")
    private Long id;
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "birthdate is required. Format: dd.MM.yyyy")
    private String birthdate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
}
