package com.seeka.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Country;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.jobs.CountryUtil;
import com.seeka.app.service.ICountryService;

@RestController
@RequestMapping("/country")
public class CountryController {
	
	@Autowired
	ICountryService countryService;
	
	@RequestMapping(value = "/get", method=RequestMethod.GET)
	public ResponseEntity<?>  getAll() {
		Map<String,Object> response = new HashMap<String, Object>();
		List<CountryDto> countryList = countryService.getAllCountries();
		response.put("status", 1);
		response.put("message","Success.!");
    	response.put("list",countryList);
    	return ResponseEntity.accepted().body(response);
	} 
	
	@RequestMapping(value = "/getwithcities", method=RequestMethod.GET)
	public ResponseEntity<?>  getWithCities() {
		Map<String,Object> response = new HashMap<String, Object>();
		List<CountryDto> countryList = CountryUtil.getCountryList();
		response.put("status", 1);
		response.put("message","Success.!");
    	response.put("list",countryList);
    	return ResponseEntity.accepted().body(response);
	} 
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable UUID id) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		System.out.println("id : "+id);
 			
		Country countryObj = countryService.get(id);
		if(null == countryObj) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Country Not Found.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.put("status", 1);
		response.put("message","Success");
		response.put("countryObj", countryObj);
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/getallinstitutecountries", method=RequestMethod.GET)
	public ResponseEntity<?>  getAllUniversityCountries() {
		Map<String,Object> response = new HashMap<String, Object>();
		List<CountryDto> countryList = countryService.getAllUniversityCountries();
		response.put("status", 1); //List<CountryDto> countryList = CountryUtil.getCountryList();
		response.put("message","Success.!"); 
    	response.put("countryList",countryList);
    	return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveCity(@RequestBody Country obj) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		obj.setCreatedOn(new Date());
		countryService.save(obj);		
		response.put("status", 1);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/discover", method=RequestMethod.GET)
	public ResponseEntity<?>  getDiscoverPage() {
		Map<String,Object> response = new HashMap<String, Object>();
		List<CountryDto> countryList = countryService.getAllCountries();
		response.put("status", 1);
		response.put("message","Success.!");
    	response.put("countryList",countryList);
    	return ResponseEntity.accepted().body(response);
	}
	
	
	 
	
}
