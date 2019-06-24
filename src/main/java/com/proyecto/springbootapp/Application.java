package com.proyecto.springbootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * clase principal desde la que ponemos en marcha el proyecto, indicamos que es una aplicación de spring boot y que permita repositorios JPA
 */
@SpringBootApplication
@EnableJpaRepositories
    public class Application  {
    /**
     * clase main
     * @param args
     */
     public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
    }

}