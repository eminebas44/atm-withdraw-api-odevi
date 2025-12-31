package com.example.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.atm.model") 
@EnableJpaRepositories("com.example.atm.repository") 
public class AtmWithdrawApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtmWithdrawApiApplication.class, args);
    }
}