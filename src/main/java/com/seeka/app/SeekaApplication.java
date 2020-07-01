
package com.seeka.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.seeka.app.util.FileStorageProperties;

@EnableScheduling
@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackages = "com.seeka.app")
@EnableJpaRepositories(basePackages = "com.seeka.app")
@EnableConfigurationProperties({ FileStorageProperties.class })
public class SeekaApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SeekaApplication.class, args);
	}

	@Bean
	@LoadBalanced // Load balance between service instances running at different ports.
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setCacheSeconds(3600);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}
}
