package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.ServiceDto;
import com.yuzee.app.endpoint.ServiceInterface;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseProcessor;

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
