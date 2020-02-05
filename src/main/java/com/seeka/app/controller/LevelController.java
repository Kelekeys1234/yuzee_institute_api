package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ILevelService levelService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> saveLevel(@RequestBody Level obj) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        levelService.save(obj);
        response.put("message", "Level added successfully");
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/country/{countryId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getLevelByCountry(@PathVariable String countryId) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Level> levelList = levelService.getLevelByCountryId(countryId);
        if (levelList != null && !levelList.isEmpty()) {
            response.put("message", "Level fetched successfully");
            response.put("status", HttpStatus.OK.value());
        } else {
            response.put("message", "Level not found");
            response.put("status", HttpStatus.NOT_FOUND.value());
        }
        response.put("data", levelList);
        return ResponseEntity.accepted().body(response);
    }
 
    @RequestMapping(value = "/course/country/{countryId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getCountryLevel(@PathVariable String countryId) throws Exception {
        return ResponseEntity.accepted().body(levelService.getCountryLevel(countryId));
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAll() throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Level> levelList = levelService.getAll();
        if (levelList != null && !levelList.isEmpty()) {
            response.put("message", "Level fetched successfully");
            response.put("status", HttpStatus.OK.value());
        } else {
            response.put("message", "Level not found");
            response.put("status", HttpStatus.NOT_FOUND.value());
        }
        response.put("data", levelList);
        return ResponseEntity.accepted().body(response);
    }
}
