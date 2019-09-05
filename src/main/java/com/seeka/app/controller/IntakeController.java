package com.seeka.app.controller;

import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.service.IInstituteTypeService;

@RestController
@RequestMapping("/intake")
public class IntakeController {

    @Autowired
    private IInstituteTypeService instituteTypeService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllIntake(@Valid @PathVariable final BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(instituteTypeService.getAllIntake());
    }
}
