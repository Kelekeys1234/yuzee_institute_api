package com.seeka.app.controller;import java.math.BigInteger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.seeka.app.util.IConstant;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
	
	@Autowired
	IFacultyService facultyService;
	
	@Autowired
	IFacultyLevelService facultyLevelService;
	 
	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveLevel(@RequestBody Faculty obj) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		facultyService.save(obj);		
		response.put("status", 1);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "level", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveFaultyLevel(@RequestBody FacultyLevel facultyLevelObj) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		facultyLevelService.save(facultyLevelObj);		
		response.put("status", 1);
		response.put("message","Success");		
		response.put("facultyLevelObj",facultyLevelObj);		
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/country/{countryId}/level/{levelId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getFacultyeByCountryAndLevelId(@PathVariable BigInteger countryId,@PathVariable BigInteger levelId) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Faculty> facultyList = facultyService.getFacultyByCountryIdAndLevelId(countryId, levelId);
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("facultyList",facultyList);
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAll() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Faculty> facultyList = facultyService.getAll();
		Map<String, Faculty> map = new HashMap<String, Faculty>();
		for (Faculty faculty : facultyList) {
			map.put(faculty.getLevel().getId()+"-"+faculty.getName(), faculty);
		}
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("facultyList",map.values());
		return ResponseEntity.accepted().body(response);
	}
	
    @RequestMapping(value = "/institute/{instituteId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getFacultyByInstituteId(@Valid @PathVariable BigInteger instituteId) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Faculty> faculties = facultyService.getFacultyByInstituteId(instituteId);
        if (faculties != null && !faculties.isEmpty()) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.FACULTY_NOT_FOUND);
        }
        response.put("facultyList", faculties);
        return ResponseEntity.accepted().body(response);
    }
    
    @RequestMapping(value = "/multiple/institute/{instituteId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getFacultyByListOfInstituteId(@Valid @PathVariable String instituteId) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Faculty> faculties = facultyService.getFacultyByListOfInstituteId(instituteId);
        if (faculties != null && !faculties.isEmpty()) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.FACULTY_NOT_FOUND);
        }
        response.put("facultyList", faculties);
        return ResponseEntity.accepted().body(response);
    }
}