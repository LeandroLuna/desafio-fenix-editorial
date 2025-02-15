package com.example.course_enrollment_system.config;

import com.example.course_enrollment_system.entity.User;
import com.example.course_enrollment_system.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;

@Configuration
public class DataLoader {
    private final PasswordEncoder passwordEncoder;

    public DataLoader(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User(
                        "admin",
                        this.passwordEncoder.encode("admin123"),
                        "ROLE_ADMIN"
                );
                userRepository.save(admin);
            }
        };
    }
}
