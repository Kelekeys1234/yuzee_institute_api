package com.seeka.freshfuture.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.freshfuture.app.bean.Country;
import com.seeka.freshfuture.app.dto.ErrorDto;
import com.seeka.freshfuture.app.service.ICountryService;


@RestController
@RequestMapping("/country")
public class CountryController {
	
	@Autowired
	ICountryService countryService;
	
	@RequestMapping(value = "/get", method=RequestMethod.GET)
	public ResponseEntity<?>  getAll() {
		Map<String,Object> response = new HashMap<String, Object>();
		List<Country> countryList = countryService.getAll();
		response.put("status", 1);
		response.put("message","Success.!");
    	response.put("list",countryList);
    	return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable Integer id) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
 			
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
	
}
