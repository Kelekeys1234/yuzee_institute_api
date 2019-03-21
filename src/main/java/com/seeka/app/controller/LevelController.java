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

import com.seeka.app.bean.Level;
import com.seeka.app.service.ILevelService;

@RestController
@RequestMapping("/level")
public class LevelController {
	
	@Autowired
	ILevelService levelService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveCity(@RequestBody Level obj) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		levelService.save(obj);		
		response.put("status", 1);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/getlevel/{countryId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getLevelByCountry(@PathVariable Integer countryId) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
        List<Level> levelList = levelService.getLevelByCountryId(countryId);
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("levelList",levelList); 
		return ResponseEntity.accepted().body(response);
		
	}
}
