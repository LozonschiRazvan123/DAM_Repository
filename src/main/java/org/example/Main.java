package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.example.Model")
@EnableJpaRepositories(basePackages = "org.example.Repository")
public class Main {
    public static void main(String[] args)
    {
        SpringApplication.run(Main.class, args);
        System.out.println("Hello world!");
    }
}