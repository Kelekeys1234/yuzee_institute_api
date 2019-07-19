package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.service.IScholarshipService;

@RestController
@RequestMapping("/scholarship")
public class ScholarshipController {

    @Autowired
    private IScholarshipService scholarshipService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> saveScholarship(@RequestBody ScholarshipDto scholarship) throws Exception {
        return ResponseEntity.accepted().body(scholarshipService.save(scholarship));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get(@PathVariable BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(scholarshipService.get(id));
    }

    @RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllScholarship(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) throws Exception {
        return ResponseEntity.accepted().body(scholarshipService.getAllScholarship(pageNumber, pageSize));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteScholarship(@PathVariable BigInteger id) {
        return ResponseEntity.accepted().body(scholarshipService.deleteScholarship(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateScolarship(@Valid @PathVariable BigInteger id, @RequestBody ScholarshipDto scholarshipDto) throws Exception {
        return ResponseEntity.accepted().body(scholarshipService.updateScholarship(id, scholarshipDto));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> search(@Valid @RequestParam("searchkey") String searchkey) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<ScholarshipDto> scholarshipList = scholarshipService.getScholarshipBySearchKey(searchkey);
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("scholarshipList", scholarshipList);
        return ResponseEntity.accepted().body(response);
    }
}
