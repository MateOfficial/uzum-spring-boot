package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(RootController.class)
public class RootControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService; // замокирован, чтобы удовлетворить требования контекста
    @MockBean
    private CarService carService;
    @MockBean
    private PersonWithCarsService personWithCarsService;
    @MockBean
    private StatisticsService statisticsService;

    @Test
    void root_ReturnsMessage() throws Exception {
    // Корневой путь теперь отдаёт статический UI; health эндпоинт перенесён на /health
    mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
        .andExpect(content().string("API is up. Use /statistics, /person, /car, /personwithcars"));
    }
}
