package com.seeka.app.controller;import java.math.BigInteger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.service.IEducationSystemService;

@RestController
@RequestMapping("/education")
public class EducationSystemController {
	
	@Autowired
	IEducationSystemService educationSystemService; 
 
	 
	@RequestMapping(value = "/system/{countryId}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> getEducationSystems(@PathVariable BigInteger countryId) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<EducationSystem> educationSystems = educationSystemService.getAll();
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("eduSystemList",educationSystems);
		return ResponseEntity.accepted().body(response);
	}
	
	
	@RequestMapping(value = "/system", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> getEducationSystems(@Valid @RequestBody EducationSystem educationSystem) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		educationSystem.setCreatedBy("AUTO");
		educationSystem.setCreatedOn(new Date());
		educationSystem.setIsActive(true);
		//educationSystem.setId(BigInteger.randomBigInteger());
		educationSystem.setIsDeleted(false);
		educationSystemService.save(educationSystem);
		List<EducationSystem> educationSystems = educationSystemService.getAll();
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("eduSystemList",educationSystems);
		return ResponseEntity.accepted().body(response);
	}
	 
}
