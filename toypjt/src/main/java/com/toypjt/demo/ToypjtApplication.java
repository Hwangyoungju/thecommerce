package com.toypjt.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.toypjt.demo.dao.UserRepository;

@SpringBootApplication
public class ToypjtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToypjtApplication.class, args);
	}

	@Bean
	public TestDataInit testDataInit(UserRepository userRepository) {
		return new TestDataInit(userRepository);
	}
}
