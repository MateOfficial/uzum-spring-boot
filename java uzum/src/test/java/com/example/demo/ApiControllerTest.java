package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;
    @MockBean
    private CarService carService;
    @MockBean
    private PersonWithCarsService personWithCarsService;
    @MockBean
    private StatisticsService statisticsService;

    @BeforeEach
    void setup() {
        // Простейшие моки
    }

    @Test
    void postPerson_Ok() throws Exception {
        String json = "{\"id\":1,\"name\":\"John\",\"birthdate\":\"01.01.2000\"}";
        doNothing().when(personService).savePerson(any(PersonRequest.class));
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void postPerson_BadRequest() throws Exception {
        String json = "{\"id\":1}"; // если нехватает полей то валидация должна провалиться
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postCar_Ok() throws Exception {
        String json = "{\"id\":10,\"model\":\"BMW-X5\",\"horsepower\":200,\"ownerId\":1}";
        doNothing().when(carService).saveCar(any(CarRequest.class));
        mockMvc.perform(post("/car").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    void getPersonWithCars_NotFound() throws Exception {
        when(personWithCarsService.getPersonWithCars(5L)).thenThrow(new RuntimeException("Person not found"));
        mockMvc.perform(get("/personwithcars").param("personid", "5"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void getStatistics_ReturnsObject() throws Exception {
        StatisticsResponse resp = new StatisticsResponse();
        resp.setPersoncount(0L); resp.setCarcount(0L); resp.setUniquevendorcount(0L);
        when(statisticsService.getStatistics()).thenReturn(resp);
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personcount").value(0))
                .andExpect(jsonPath("$.carcount").value(0))
                .andExpect(jsonPath("$.uniquevendorcount").value(0));
    }
}
