package ru.nikitin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.SpringVersion;

@SpringBootApplication
@EntityScan(basePackages = {"ru.nikitin.entity.*"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}