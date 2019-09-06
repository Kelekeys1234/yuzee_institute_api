package com.seeka.app.service;

import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.seeka.app.dto.EducationAgentDto;

public interface IEducationAgentService {
    
    public ResponseEntity<?> save(@Valid EducationAgentDto educationAgentDto); 

    public ResponseEntity<?> update(@Valid EducationAgentDto educationAgentDto, BigInteger id); 

}
