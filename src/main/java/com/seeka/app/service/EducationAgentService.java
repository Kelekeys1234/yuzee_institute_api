package com.seeka.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seeka.app.dao.IEducationAgentDAO;

@Service
public class EducationAgentService implements IEducationAgentService{

    @Autowired
    IEducationAgentDAO educationAgentDao; 
    
}
