package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Education Agent Save Successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }

        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEducationAgent(@PathVariable BigInteger id, @RequestBody final EducationAgentDto educationAgentDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            educationService.update(educationAgentDto, id);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Education Agent Update Successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        return ResponseEntity.accepted().body(response);
    }
    
    @RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllEducationAgent(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception {
        return ResponseEntity.accepted().body(educationService.getAllEducationAgent(pageNumber, pageSize));
    }
}
