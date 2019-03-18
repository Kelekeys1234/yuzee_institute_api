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

import com.seeka.freshfuture.app.bean.City;
import com.seeka.freshfuture.app.dto.ErrorDto;
import com.seeka.freshfuture.app.service.ICityService;


@RestController
@RequestMapping("/city")
public class CityController {
	
	@Autowired
	ICityService cityService;
	
	@RequestMapping(value = "/get", method=RequestMethod.GET)
	public ResponseEntity<?>  getAll() throws Exception{
		Map<String,Object> response = new HashMap<String, Object>();
		List<City> cityList = cityService.getAll();
		response.put("status", 1);
		response.put("message","Success.!");
    	response.put("cityList",cityList);
    	return ResponseEntity.accepted().body(response);
	}
	@RequestMapping(value = "/getallcitiesbycountry/{countryId}", method=RequestMethod.GET)
	public ResponseEntity<?>  getAllCitiesByCountry(@PathVariable Integer countryId) throws Exception {
		Map<String,Object> response = new HashMap<String, Object>();
		List<City> cityList = cityService.getAllCitiesByCountry(countryId);
		response.put("status", 1);
		response.put("message","Success.!");
    	response.put("cityList",cityList);
    	return ResponseEntity.accepted().body(response);
	}	
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable Integer id) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		City cityObj = cityService.get(id);
		if(null == cityObj) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("City Not Found.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		response.put("status", 1);
		response.put("message","Success");
		response.put("cityObj", cityObj);
		return ResponseEntity.accepted().body(response);
	}
	
}
