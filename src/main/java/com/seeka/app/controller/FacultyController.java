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

import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.FacultyLevel;
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
	public ResponseEntity<?> saveLevel(@RequestBody Faculty obj) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		facultyService.save(obj);		
		response.put("status", 1);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "facultylevel/save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveFaultyLevel(@RequestBody FacultyLevel facultyLevelObj) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		facultyLevelService.save(facultyLevelObj);		
		response.put("status", 1);
		response.put("message","Success");		
		response.put("facultyLevelObj",facultyLevelObj);		
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/get/{countryid}/{levelid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getFacultyeByCountryAndCourseType(@PathVariable Integer countryid,@PathVariable Integer levelid) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Faculty> facultyList = facultyService.getFacultyByCountryIdAndLevelId(countryid, levelid);
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("facultyList",facultyList);
		return ResponseEntity.accepted().body(response);
	}
	
}
