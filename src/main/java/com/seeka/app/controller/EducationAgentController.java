package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.EducationAgentDto;
import com.seeka.app.service.IEducationAgentService;

@RestController
@RequestMapping("/educationAgent")
@Transactional
public class EducationAgentController {

    @Autowired
    private IEducationAgentService educationService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveEducationAgent(@RequestBody final EducationAgentDto educationAgentDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            educationService.save(educationAgentDto);
            response.put("status", 200);
            response.put("message", "Education Agent Save Successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("status", 400);
            response.put("message", "Error to save Education Agent");
        }

        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEducationAgent(@PathVariable BigInteger id, @RequestBody final EducationAgentDto educationAgentDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            educationService.update(educationAgentDto, id);
            response.put("status", 200);
            response.put("message", "Education Agent Update Successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("status", 400);
            response.put("message", "Error to update Education Agent");
        }
        return ResponseEntity.accepted().body(response);
    }
}
