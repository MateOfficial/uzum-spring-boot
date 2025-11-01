package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Optional;

public class PersonServiceTest {
    @InjectMocks
    PersonService personService;
    @Mock
    PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePerson_Ok() {
        PersonRequest req = new PersonRequest();
        req.setId(1L);
        req.setName("John");
        req.setBirthdate("01.01.2000");
        when(personRepository.existsById(1L)).thenReturn(false);
        personService.savePerson(req);
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void savePerson_TooYoung() {
        PersonRequest req = new PersonRequest();
        req.setId(2L);
        req.setName("Mike");
        req.setBirthdate("01.01.2020"); // слишком молодой человек
        when(personRepository.existsById(2L)).thenReturn(false);
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> {
            personService.savePerson(req);
        });
        Assertions.assertTrue(ex.getMessage().contains("18 or older"));
    }

    @Test
    void savePerson_BirthdayInFuture() {
        PersonRequest req = new PersonRequest();
        req.setId(5L);
        req.setName("Tom");
        req.setBirthdate("01.01.2100");
        when(personRepository.existsById(5L)).thenReturn(false);
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> personService.savePerson(req));
        Assertions.assertTrue(ex.getMessage().contains("future"));
    }
}
