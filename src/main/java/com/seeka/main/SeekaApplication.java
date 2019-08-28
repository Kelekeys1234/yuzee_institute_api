
package com.seeka.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.seeka.app.util.FileStorageProperties;


@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({ FileStorageProperties.class })
@ComponentScan(basePackages="com.seeka.app")
@EnableEurekaClient
public class SeekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeekaApplication.class, args);
	}
	
	@Bean
	@LoadBalanced // Load balance between service instances running at different ports.
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
