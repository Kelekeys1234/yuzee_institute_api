package com.seeka.app.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.dto.EducationAgentDto;
import com.seeka.app.dto.EducationAgentPartnershipsDto;

public interface IEducationAgentService {

    public void save(@Valid EducationAgentDto educationAgentDto);

    public void update(@Valid EducationAgentDto educationAgentDto, String id);

    public Map<String, Object> getAllEducationAgent(Integer pageNumber, Integer pageSize);

    public Map<String, Object> get(@Valid String id);

    public void savePartnership(List<EducationAgentPartnershipsDto> agentPartnershipsDto);

    public Map<String, Object> deleteEducationAgent(String id);

    public Map<String, Object> getAllSkill();
}
