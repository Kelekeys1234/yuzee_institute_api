package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.AgentEducationDetail;
import com.seeka.app.bean.AgentMediaDocumentation;
import com.seeka.app.bean.AgentServiceOffered;
import com.seeka.app.bean.EducationAgent;
import com.seeka.app.bean.EducationAgentAccomplishment;
import com.seeka.app.bean.EducationAgentPartnerships;
import com.seeka.app.bean.EducationAgentSkill;
import com.seeka.app.dto.EducationAgentGetAllDto;

public interface EducationAgentDao {

    public void saveEducationAgent(EducationAgent educationAgent);

    public void updateEducationAgent(EducationAgent educationAgent);

    public EducationAgent fetchEducationAgent(String id);

    public void deleteEducationAgent(String id);

    public List<EducationAgentSkill> fetchEducationAgentSkillByEducationAgentId(String educationAgent);

    public List<AgentServiceOffered> fetchAgentServiceOffered(String educationAgent);

    public List<AgentEducationDetail> fetchAgentEducationDetail(String educationAgent);

    public List<AgentMediaDocumentation> fetchAgentMediaDocumentation(String educationAgent);

    public List<EducationAgentAccomplishment> fetcheducationAgentAccomplishment(String educationAgent);

    public void saveEducationAgentSkill(EducationAgentSkill agentSkill);

    public void deleteEducationAgentSkill(String educationAgentId);

    public void saveAgentServiceOffered(AgentServiceOffered agentServiceOffered);

    public void deleteAgentServiceOffered(String educationAgentId);

    public void saveAgentEducationDetail(AgentEducationDetail agentEducationDetail);

    public void deleteAgentEducationDetail(String educationAgentId);

    public void saveAgentMediaDocumentation(AgentMediaDocumentation agentMediaDocumentation);

    public void deleteAgentMediaDocumentation(String educationAgentId);

    public EducationAgent get(String id);

    public int findTotalCount();

    public List<EducationAgentGetAllDto> getAll(int startIndex, Integer pageSize);

    public void saveEducationAgentPartnerships(EducationAgentPartnerships educationAgentPartnerships);

    public void deleteEducationAgentPartnerships(String educationAgent);

    public void saveEducationAgentAccomplishment(EducationAgentAccomplishment educationAgentAccomplishment);

    public void deleteEducationAgentAccomplishment(String educationAgent);

}
