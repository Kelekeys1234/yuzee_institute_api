package com.seeka.app.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seeka.app.dto.LevelDto;

@RequestMapping("/api/v1/level")
public interface LevelInterface {

	@PostMapping
    public ResponseEntity<?> saveLevel(@RequestBody LevelDto level) throws Exception;
	
	@Deprecated
	@GetMapping("/country/{countryId}")
    public ResponseEntity<?> getLevelByCountry(@PathVariable String countryId) throws Exception;
	
	@Deprecated
	@GetMapping("/course/country/{countryId}")
    public ResponseEntity<?> getCountryLevel(@PathVariable String countryId) throws Exception;
	
	@GetMapping
    public ResponseEntity<?> getAll() throws Exception;
	
}
