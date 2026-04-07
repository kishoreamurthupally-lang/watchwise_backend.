package com.Movies.watchwise_backend;

import com.Movies.watchwise_backend.Repo.UserRepository;
import com.Movies.watchwise_backend.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WatchwiseBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatchwiseBackendApplication.class, args);
    }

    //  Auto-create Admin in DB on startup
    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepo,
                                         PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@watchwise.com";
            if (userRepo.findByEmailIgnoreCase(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("Admin@123"));
                admin.setRole("ADMIN"); //  stored as ADMIN
                admin.setVerified(true);
                userRepo.save(admin);
                System.out.println(" Admin created in DB");
            } else {
                System.out.println(" Admin already exists");
            }
        };
    }
}