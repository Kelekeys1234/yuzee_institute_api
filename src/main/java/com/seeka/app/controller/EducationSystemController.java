package com.seeka.app.controller;

import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.EducationSystemDto;
import com.seeka.app.dto.EducationSystemRequest;
import com.seeka.app.service.IEducationSystemService;

@RestController
@RequestMapping("/educationSystem")
public class EducationSystemController {

    @Autowired
    private IEducationSystemService educationSystemService;

    @RequestMapping(value = "{countryId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getEducationSystems(@PathVariable BigInteger countryId) throws Exception {
        return educationSystemService.getEducationSystemsByCountryId(countryId);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody EducationSystemDto educationSystem) throws Exception {
        return educationSystemService.save(educationSystem);
    }

    @RequestMapping(value = "/details", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> saveEducationDetails(@Valid @RequestBody EducationSystemRequest educationSystemDetails) throws Exception {
        return educationSystemService.saveEducationDetails(educationSystemDetails);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getEducationSystemsDetailByUserId(@PathVariable BigInteger userId) throws Exception {
        return educationSystemService.getEducationSystemsDetailByUserId(userId);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@Valid @PathVariable final BigInteger userId) throws Exception {
        return ResponseEntity.accepted().body(educationSystemService.deleteEducationSystemDetailByUserId(userId));
    }

    @RequestMapping(value = "/grades/{countryId}/{systemId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getGrades(@PathVariable BigInteger countryId, @PathVariable BigInteger systemId) throws Exception {
        return educationSystemService.getGrades(countryId, systemId);
    }
}
