package com.yuzee.app.endpoint;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.dto.EducationSystemDto;

@RequestMapping("/api/v1/educationSystem")
public interface EducationSystemInterface {

	@GetMapping("/{countryName}")
	public ResponseEntity<?> getEducationSystems(@PathVariable final String countryName) throws Exception;
	
	@PostMapping
	public ResponseEntity<?> saveEducationSystems(@Valid @RequestBody final EducationSystemDto educationSystem) throws Exception;
	
	@GetMapping("/grades/{countryName}/{systemId}")
	public ResponseEntity<?> getGrades(@PathVariable final String countryName, @PathVariable final String systemId) throws Exception;
	
	@GetMapping("/{countryName}/{stateName}")
	public ResponseEntity<?> getEducationSystemByCountryNameAndStateName(@PathVariable String countryName,
			@PathVariable String stateName) throws Exception;
}
