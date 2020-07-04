package com.seeka.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.ServiceDto;
import com.seeka.app.endpoint.ServiceInterface;
import com.seeka.app.processor.CourseProcessor;

@RestController("serviceControllerV1")
public class ServiceController implements ServiceInterface {
    
    @Autowired
    private CourseProcessor courseProcessor;

    @Override
    public ResponseEntity<?> getAllServices() throws Exception {
    	List<ServiceDto> serviceDtos = courseProcessor.getAllServices();
        return new GenericResponseHandlers.Builder().setData(serviceDtos)
				.setMessage("Services fetched successfully").setStatus(HttpStatus.OK).create();
    }
}
