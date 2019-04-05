package com.seeka.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.CountryDto;
import com.seeka.app.jobs.CountryLevelFacultyUtil;
import com.seeka.app.service.ICountryService;
import com.seeka.app.service.IFacultyService;
import com.seeka.app.service.ILevelService;

@RestController
@RequestMapping("/search")
public class SearchPageController {
	
	@Autowired
	ICountryService countryService;
	
	@Autowired
	ILevelService levelService;
	
	@Autowired
	IFacultyService facultyService;
	
	
	@RequestMapping(value = "/get/all", method=RequestMethod.GET)
	public ResponseEntity<?>  getAllCountries() {
		Map<String,Object> response = new HashMap<String, Object>();
		List<CountryDto> countryList = CountryLevelFacultyUtil.getCountryList();
		System.out.println("Countries: "+countryList.size());
		response.put("countryList",countryList);
		response.put("status", 1);
		response.put("message","Success.!");
    	return ResponseEntity.accepted().body(response);
	} 
	
	 
	
}
