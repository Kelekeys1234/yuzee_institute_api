package com.seeka.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.City;
import com.seeka.app.bean.InstituteServiceDetails;
import com.seeka.app.bean.ServiceDetails;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.service.ICityService;
import com.seeka.app.service.IInstituteServiceDetailsService;
import com.seeka.app.service.IServiceDetailsService;


@RestController
@RequestMapping("/institue")
public class InstituteServiceController {
	
	@Autowired
	IInstituteServiceDetailsService instituteService;
	
	@Autowired
	IServiceDetailsService service;
	
	
	@RequestMapping(value = "instituteservice/save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveInstituteService(@RequestBody InstituteServiceDetails obj) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		instituteService.save(obj);		
		response.put("status", 1);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
	
	@RequestMapping(value = "service/save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveService(@RequestBody ServiceDetails obj) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		service.save(obj);		
		response.put("status", 1);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
}
