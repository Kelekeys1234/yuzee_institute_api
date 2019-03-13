package com.seeka.freshfuture.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.seeka.freshfuture.app")
public class FreshFutureApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreshFutureApplication.class, args);
	}

}
