package com.example.eventmanager.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotenvLoader implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        Map<String, Object> env = new HashMap<>();
        dotenv.entries().forEach(entry -> env.put(entry.getKey(), entry.getValue()));
        context.getEnvironment().getPropertySources().addFirst(new MapPropertySource("dotenv", env));
    }
}
