package com.seeka.app.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.City;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.jobs.CountryUtil;
import com.seeka.app.service.ICityService;
import com.seeka.app.util.NumbeoWebServiceClient;


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
	@RequestMapping(value = "/byCountryId/{countryId}", method=RequestMethod.GET)
	public ResponseEntity<?>  getAllCitiesByCountry(@PathVariable UUID countryId) throws Exception {
		Map<String,Object> response = new HashMap<String, Object>();
		List<City> cityList = cityService.getAllCitiesByCountry(countryId);
		response.put("status", 1);
		response.put("message","Success.!");
    	response.put("cityList",cityList);
    	return ResponseEntity.accepted().body(response);
	}	
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable UUID id) throws Exception {
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
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveCity(@RequestBody City obj) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
	    cityService.save(obj);		
		response.put("status", 1);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
	
	@RequestMapping(value = "/update/pricing", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> updateCityPricing() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<City> list = cityService.getAll();
		for (City city : list) {
			try {
				NumbeoWebServiceClient.getCityPricing(city.getName(), city.getId());
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		response.put("status", 1);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/pricing/{cityid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCityPricingByCityID(@PathVariable UUID cityid) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		File file = new File(NumbeoWebServiceClient.fileDirectory+cityid+".json");		
		City city = cityService.get(cityid);		
		if(null != file && file.exists()) {
			//Already file there on the directory.			
		}else {	
			NumbeoWebServiceClient.getCityPricing(city.getName(), city.getId());
			file = new File(NumbeoWebServiceClient.fileDirectory+cityid+".json");			
		}
		JSONObject jsonObject = new JSONObject();	
		if(null != file && file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;		
			String responseStr = "";
			while ((st = br.readLine()) != null) {
				responseStr += st;
				System.out.println(responseStr); 
			}			
			br.close();	
			JSONParser parser = new JSONParser();
			jsonObject =  (JSONObject) parser.parse(responseStr);			
		}								
		response.put("status", 1);
		response.put("message","Success");
		response.put("cityName",city.getName()+", "+CountryUtil.getCountryByCountryId(city.getCountryId()).getName());
		response.put("livingCost",jsonObject);
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/multiByCountryId/{countryId}", method=RequestMethod.GET)
    public ResponseEntity<?>  getAllMultipleCitiesByCountry(@PathVariable String countryId) throws Exception {
        Map<String,Object> response = new HashMap<String, Object>();
        List<City> cityList = cityService.getAllMultipleCitiesByCountry(countryId);
        response.put("status", 1);
        response.put("message","Success.!");
        response.put("cityList",cityList);
        return ResponseEntity.accepted().body(response);
    }   
	
}
