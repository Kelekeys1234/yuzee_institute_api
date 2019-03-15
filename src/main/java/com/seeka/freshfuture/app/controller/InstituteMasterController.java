package com.seeka.freshfuture.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.freshfuture.app.bean.Country;
import com.seeka.freshfuture.app.bean.CourseType;
import com.seeka.freshfuture.app.bean.Faculty;
import com.seeka.freshfuture.app.service.ICountryService;
import com.seeka.freshfuture.app.service.ICourseTypeService;
import com.seeka.freshfuture.app.service.IFacultyService;

@RestController
@RequestMapping("/institute/master")
public class InstituteMasterController {

	@Autowired
	IFacultyService facultyService;
	
	@Autowired
	ICourseTypeService courseTypeService;
	
	@Autowired
	ICountryService countryService;

	@RequestMapping(value = "/getsearchpagefiltervalues", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getSearchPageFilterValues() throws Exception {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
        List<CourseType> courseTypeList = courseTypeService.getAll();
        
        List<Country> countryList = countryService.getAll();
        
        List<Faculty> facultyList = facultyService.getAll();
        
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("courseTypeList",courseTypeList);
		response.put("facultyList",facultyList);
		response.put("countryList",countryList);
		return ResponseEntity.accepted().body(response);
		
	}

}
