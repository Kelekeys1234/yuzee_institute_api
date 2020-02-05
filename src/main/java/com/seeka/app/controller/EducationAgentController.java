package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
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
import com.seeka.app.dto.EducationAgentPartnershipsDto;
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
    public ResponseEntity<?> updateEducationAgent(@PathVariable String id, @RequestBody final EducationAgentDto educationAgentDto) {
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getEducationAgent(@PathVariable final String id) throws Exception {
        return ResponseEntity.accepted().body(educationService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEducationAgent(@PathVariable String id) {
        return ResponseEntity.accepted().body(educationService.deleteEducationAgent(id));
    }

    @RequestMapping(value = "/partnership", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> saveEducationAgentPartnership(@RequestBody final List<EducationAgentPartnershipsDto> agentPartnershipsDto) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
            educationService.savePartnership(agentPartnershipsDto);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Education Agent Partnership Save Successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        return ResponseEntity.accepted().body(response);
    }
}
