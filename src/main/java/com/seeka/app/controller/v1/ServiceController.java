package com.seeka.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.service.ICourseService;

@RestController("serviceControllerV1")
@RequestMapping("/api/v1/service")
public class ServiceController {
    
    @Autowired
    private ICourseService courseService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllServices() throws Exception {
        return ResponseEntity.accepted().body(courseService.getAllServices());
    }
}
