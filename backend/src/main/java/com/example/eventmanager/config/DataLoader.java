package com.example.eventmanager.config;

import com.example.eventmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.findByEmail("joao@example.com")
                .ifPresent(user -> {
                    userRepository.delete(user);
                    System.out.println("🗑️  Usuário removido.");
                });
    }

}
