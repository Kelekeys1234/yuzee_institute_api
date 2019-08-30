package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.dto.ScholarshipFilterDto;
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
        if (scholarshipList != null && !scholarshipList.isEmpty()) {
            response.put("message", "Scholarship fetched successfully");
            response.put("status", HttpStatus.OK.value());
        } else {
            response.put("message", "Scholarship not found");
            response.put("status", HttpStatus.NOT_FOUND.value());
        }
        response.put("data", scholarshipList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> scholarshipFilter(@RequestBody final ScholarshipFilterDto scholarshipFilterDto) throws Exception {
        return ResponseEntity.ok().body(scholarshipService.scholarshipFilter(scholarshipFilterDto));
    }
}
