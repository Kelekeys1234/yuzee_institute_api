package com.yuzee.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class Runner implements CommandLineRunner{
	@Autowired
	private ServiceRepository serviceRepository;
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
