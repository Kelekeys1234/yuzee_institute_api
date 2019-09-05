package com.seeka.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.service.ICurrencyService;
import com.seeka.app.service.IEducationAgentService;

@RestController
@RequestMapping("/educationAgent")
public class EducationAgentController {

    @Autowired
    private IEducationAgentService educationService;
}
