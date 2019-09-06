package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.dto.EducationAgentDto;

public interface IEducationAgentService {

    public void save(@Valid EducationAgentDto educationAgentDto);

    public void update(@Valid EducationAgentDto educationAgentDto, BigInteger id);

    public Map<String, Object> getAllEducationAgent(Integer pageNumber, Integer pageSize);

}
