package com.reservation.entity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.reservation"})
@EnableJpaAuditing
@EntityScan("com.reservation.entity")
@EnableJpaRepositories(basePackages = "com.reservation.repository")
public class ReservationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReservationApplication.class, args);
    }
}



