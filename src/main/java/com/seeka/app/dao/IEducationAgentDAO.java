package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.AgentEducationDetail;
import com.seeka.app.bean.AgentMediaDocumentation;
import com.seeka.app.bean.AgentServiceOffered;
import com.seeka.app.bean.EducationAgent;
import com.seeka.app.bean.EducationAgentAccomplishment;
import com.seeka.app.bean.EducationAgentPartnerships;
import com.seeka.app.bean.EducationAgentSkill;
import com.seeka.app.bean.Skill;
import com.seeka.app.dto.EducationAgentGetAllDto;

public interface IEducationAgentDAO {

    public void saveEducationAgent(EducationAgent educationAgent);

    public void updateEducationAgent(EducationAgent educationAgent);

    public EducationAgent fetchEducationAgent(BigInteger id);

    public void deleteEducationAgent(BigInteger id);

    public List<EducationAgentSkill> fetchEducationAgentSkillByEducationAgentId(BigInteger educationAgent);

    public List<AgentServiceOffered> fetchAgentServiceOffered(BigInteger educationAgent);

    public List<AgentEducationDetail> fetchAgentEducationDetail(BigInteger educationAgent);

    public List<AgentMediaDocumentation> fetchAgentMediaDocumentation(BigInteger educationAgent);

    public List<EducationAgentAccomplishment> fetcheducationAgentAccomplishment(BigInteger educationAgent);

    public void saveSkill(Skill skill);

    public Skill fetchSkill(BigInteger id);

    public void deleteSkill(BigInteger educationAgentId);

    public void saveEducationAgentSkill(EducationAgentSkill agentSkill);

    public void deleteEducationAgentSkill(BigInteger educationAgentId);

    public void saveAgentServiceOffered(AgentServiceOffered agentServiceOffered);

    public void deleteAgentServiceOffered(BigInteger educationAgentId);

    public void saveAgentEducationDetail(AgentEducationDetail agentEducationDetail);

    public void deleteAgentEducationDetail(BigInteger educationAgentId);

    public void saveAgentMediaDocumentation(AgentMediaDocumentation agentMediaDocumentation);

    public void deleteAgentMediaDocumentation(BigInteger educationAgentId);

    public EducationAgent get(BigInteger id);

    public int findTotalCount();

    public List<EducationAgentGetAllDto> getAll(int startIndex, Integer pageSize);

    public void saveEducationAgentPartnerships(EducationAgentPartnerships educationAgentPartnerships);

    public void deleteEducationAgentPartnerships(BigInteger educationAgent);

    public void saveEducationAgentAccomplishment(EducationAgentAccomplishment educationAgentAccomplishment);

    public void deleteEducationAgentAccomplishment(BigInteger educationAgent);

}
