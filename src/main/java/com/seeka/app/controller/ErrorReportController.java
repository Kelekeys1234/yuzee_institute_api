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

import com.seeka.app.dto.ErrorReportDto;
import com.seeka.app.service.IErrorReportService;

@RestController
@RequestMapping("/error/report")
public class ErrorReportController {

    @Autowired
    private IErrorReportService errorReportService;

    @RequestMapping(value = "/category", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllErrorCategory() throws Exception {
        return errorReportService.getAllErrorCategory();
    }
    
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody ErrorReportDto errorReport) throws Exception {
        return errorReportService.save(errorReport);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getErrorReportByUserId(@PathVariable BigInteger userId) throws Exception {
        return errorReportService.getErrorReportByUserId(userId);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get() throws Exception {
        return errorReportService.getAllErrorReport();
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@Valid @PathVariable final BigInteger userId) throws Exception {
        return ResponseEntity.accepted().body(errorReportService.deleteByUserId(userId));
    }
}
