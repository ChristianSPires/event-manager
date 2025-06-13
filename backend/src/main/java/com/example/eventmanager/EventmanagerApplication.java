package com.example.eventmanager;

import com.example.eventmanager.config.DotenvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventmanagerApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EventmanagerApplication.class);
		app.addInitializers(new DotenvLoader());
		app.run(args);
	}
}
