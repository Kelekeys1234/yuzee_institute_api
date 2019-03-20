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
import com.seeka.app.bean.Faculty;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.service.ICityService;
import com.seeka.app.service.IFacultyLevelService;
import com.seeka.app.service.IFacultyService;


@RestController
@RequestMapping("/faculty")
public class FacultyController {
	
	@Autowired
	IFacultyService facultyService;
	@Autowired
	IFacultyLevelService facultyLevelService;
	
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveCity(@RequestBody Faculty obj) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		facultyService.save(obj);		
		response.put("status", 1);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
}
