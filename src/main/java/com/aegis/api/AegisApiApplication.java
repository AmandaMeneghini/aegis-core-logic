package com.aegis.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AegisApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AegisApiApplication.class, args);
    }
}