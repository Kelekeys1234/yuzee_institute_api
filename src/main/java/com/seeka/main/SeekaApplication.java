package com.seeka.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.seeka.freshfuture.app")
public class SeekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeekaApplication.class, args);
	}

}
