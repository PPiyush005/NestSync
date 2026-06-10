package com.SocietyManagementSystem.SocietyManagementSystem_Backend;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.Role;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.User;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocietyManagementSystemBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocietyManagementSystemBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner createDefaultAdmin(UserRepository userRepository,
                                                PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Default admin created — username: admin, password: admin123");
            }
        };
    }
}