package com.sdewa.BasicSpring;

import org.springframework.boot.SpringApplication;

public class TestBasicSpringApplication {

	public static void main(String[] args) {
		SpringApplication.from(BasicSpringApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
