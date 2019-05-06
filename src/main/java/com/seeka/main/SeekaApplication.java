
package com.seeka.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.seeka.app.util.FileStorageProperties;


@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({ FileStorageProperties.class })
@ComponentScan(basePackages="com.seeka.app")
public class SeekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeekaApplication.class, args);
	}

}
