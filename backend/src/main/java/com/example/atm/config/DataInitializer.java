package com.example.atm.config;

import com.example.atm.model.User;
import com.example.atm.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            if (repository.findByUsername("1111222233334444").isEmpty()) {
                repository.save(new User(null, "1111222233334444", "1234", 1500.50));
            }
            if (repository.findByUsername("5555666677778888").isEmpty()) {
                repository.save(new User(null, "5555666677778888", "4321", 500.00));
            }
            System.out.println("Test kullanıcıları veritabanına kontrol edildi/eklendi.");
        };
    }
}