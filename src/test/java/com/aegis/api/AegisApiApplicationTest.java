package com.aegis.api;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class AegisApiApplicationTest {

    @Test
    void contextLoads() {
    }

    @Test
    void mainMethodShouldCallSpringApplicationRun() {
        String[] args = new String[] {};

        try (MockedStatic<SpringApplication> mockedSpringApp = mockStatic(SpringApplication.class)) {
            AegisApiApplication.main(args);

            mockedSpringApp.verify(() ->
                    SpringApplication.run(AegisApiApplication.class, args)
            );
        }
    }
}
