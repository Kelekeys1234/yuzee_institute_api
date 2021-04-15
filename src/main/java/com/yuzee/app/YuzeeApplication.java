package com.yuzee.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.yuzee.app.processor.MasterDataImportProcessor;
import com.yuzee.app.util.FileStorageProperties;

@Configuration
@EnableScheduling
@EnableEurekaClient
@SpringBootApplication(exclude = {ContextRegionProviderAutoConfiguration.class, ContextStackAutoConfiguration.class})
@EntityScan(basePackages = "com.yuzee.app")
@EnableJpaRepositories(basePackages = "com.yuzee.app")
@EnableConfigurationProperties({ FileStorageProperties.class })
@EnableAsync
@EnableCaching
public class YuzeeApplication {

	@Autowired
	private MasterDataImportProcessor masterDataImportProcessor;
	
	public static void main(final String[] args) {
		SpringApplication.run(YuzeeApplication.class, args);
	}

	@Bean
	@LoadBalanced // Load balance between service instances running at different ports.
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean("fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(10);
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
	
	@EventListener(ApplicationStartedEvent.class)
	public void appInit() {
		masterDataImportProcessor.importInstituteCategoryType();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.getConfiguration().setDeepCopyEnabled(true);
		return modelMapper;
	}
}
