package com.seeka.app.service;

import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.seeka.app.bean.AgentEducationDetail;
import com.seeka.app.bean.AgentMediaDocumentation;
import com.seeka.app.bean.AgentServiceOffered;
import com.seeka.app.bean.EducationAgent;
import com.seeka.app.bean.EducationAgentSkill;
import com.seeka.app.bean.Skill;
import com.seeka.app.dao.ICityDAO;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.IEducationAgentDAO;
import com.seeka.app.dao.IServiceDetailsDAO;
import com.seeka.app.dto.AgentEducationDetailDto;
import com.seeka.app.dto.AgentMediaDocumentationDto;
import com.seeka.app.dto.AgentServiceOfferedDto;
import com.seeka.app.dto.EducationAgentDto;
import com.seeka.app.util.DateUtil;

@Service
public class EducationAgentService implements IEducationAgentService {

    @Autowired
    IEducationAgentDAO educationAgentDao;
    @Autowired
    ICountryDAO countryDao;
    @Autowired
    ICityDAO cityDao;
    @Autowired
    IServiceDetailsDAO serviceDao;

    @Override
    public ResponseEntity<?> save(@Valid EducationAgentDto educationAgentDto) {
        try {
            EducationAgent educationAgent = convertEducationAgentDtoToEducationAgent(educationAgentDto);
            educationAgentDao.saveEducationAgent(educationAgent);
            if (educationAgentDto.getSkill() != null && !educationAgentDto.getSkill().isEmpty()) {
                saveSkillAndEducationAgentSkill(educationAgentDto, educationAgent);
            }
            if (educationAgentDto.getAgentServiceOffereds() != null && !educationAgentDto.getAgentServiceOffereds().isEmpty()) {
                saveAgentServiceOffered(educationAgentDto, educationAgent);
            }
            if (educationAgentDto.getAgentEducationDetails() != null && !educationAgentDto.getAgentEducationDetails().isEmpty()) {
                saveAgentEducationDetails(educationAgentDto, educationAgent);
            }
            if (educationAgentDto.getAgentMediaDocumentations() != null && !educationAgentDto.getAgentMediaDocumentations().isEmpty()) {
                saveAgentMediaDocumentation(educationAgentDto, educationAgent);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<?> update(@Valid EducationAgentDto educationAgentDto, BigInteger id) {
        try {
            EducationAgent educationAgent = convertEducationAgentDtoToEducationAgentForUpdate(educationAgentDto, id);
            educationAgentDao.updateEducationAgent(educationAgent);
            if (educationAgentDto.getSkill() != null && !educationAgentDto.getSkill().isEmpty()) {
                saveSkillAndEducationAgentSkill(educationAgentDto, educationAgent);
            }
            if (educationAgentDto.getAgentServiceOffereds() != null && !educationAgentDto.getAgentServiceOffereds().isEmpty()) {
                saveAgentServiceOffered(educationAgentDto, educationAgent);
            }
            if (educationAgentDto.getAgentEducationDetails() != null && !educationAgentDto.getAgentEducationDetails().isEmpty()) {
                saveAgentEducationDetails(educationAgentDto, educationAgent);
            }
            if (educationAgentDto.getAgentMediaDocumentations() != null && !educationAgentDto.getAgentMediaDocumentations().isEmpty()) {
                saveAgentMediaDocumentation(educationAgentDto, educationAgent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public EducationAgent convertEducationAgentDtoToEducationAgentForUpdate(EducationAgentDto educationAgentDto, BigInteger id) {
        EducationAgent agent = new EducationAgent();
        agent.setId(id);
        agent.setFirstName(educationAgentDto.getFirstName());
        agent.setLastName(educationAgentDto.getLastName());
        agent.setDescription(educationAgentDto.getDescription());
        agent.setCountry(countryDao.get(educationAgentDto.getCity()));
        agent.setCity(cityDao.get(educationAgentDto.getCountry()));
        agent.setCreatedBy(educationAgentDto.getCreatedBy());
        agent.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
        agent.setUpdatedBy(educationAgentDto.getUpdatedBy());
        agent.setUpdatedOn(educationAgentDto.getUpdatedOn());
        agent.setDeletedBy(educationAgentDto.getDeletedBy());
        agent.setDeletedOn(educationAgentDto.getDeletedOn());
        agent.setPhoneNumber(educationAgentDto.getPhoneNumber());
        agent.setEmail(educationAgentDto.getEmail());
        return agent;
    }

    public EducationAgent convertEducationAgentDtoToEducationAgent(EducationAgentDto educationAgentDto) {
        EducationAgent agent = new EducationAgent();
        agent.setFirstName(educationAgentDto.getFirstName());
        agent.setLastName(educationAgentDto.getLastName());
        agent.setDescription(educationAgentDto.getDescription());
        agent.setCountry(countryDao.get(educationAgentDto.getCountry()));
        agent.setCity(cityDao.get(educationAgentDto.getCity()));
        agent.setCreatedBy(educationAgentDto.getCreatedBy());
        agent.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
        agent.setUpdatedBy(educationAgentDto.getUpdatedBy());
        agent.setUpdatedOn(educationAgentDto.getUpdatedOn());
        agent.setDeletedBy(educationAgentDto.getDeletedBy());
        agent.setDeletedOn(educationAgentDto.getDeletedOn());
        agent.setPhoneNumber(educationAgentDto.getPhoneNumber());
        agent.setEmail(educationAgentDto.getEmail());
        return agent;
    }

    public void saveSkillAndEducationAgentSkill(EducationAgentDto educationAgentDto, EducationAgent educationAgent) {
        educationAgentDao.deleteEducationAgentSkill(educationAgent.getId());
        for (Skill skills : educationAgentDto.getSkill()) {
            EducationAgentSkill educationAgentSkill = new EducationAgentSkill();
            Skill skill = educationAgentDao.fetchSkill(skills.getId());
            educationAgentSkill.setSkillId(skill);
            educationAgentSkill.setEducationAgent(educationAgent);
            educationAgentSkill.setCreatedBy(educationAgentDto.getCreatedBy());
            educationAgentSkill.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
            educationAgentSkill.setUpdatedBy(educationAgentDto.getUpdatedBy());
            educationAgentSkill.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
            educationAgentSkill.setDeletedBy(educationAgentDto.getDeletedBy());
            educationAgentSkill.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
            educationAgentDao.saveEducationAgentSkill(educationAgentSkill);
        }
    }

    public void saveAgentServiceOffered(EducationAgentDto educationAgentDto, EducationAgent educationAgent) {
        educationAgentDao.deleteAgentServiceOffered(educationAgent.getId());
        for (AgentServiceOfferedDto offeredDto : educationAgentDto.getAgentServiceOffereds()) {
            AgentServiceOffered agentServiceOffered = new AgentServiceOffered();
            agentServiceOffered.setService(serviceDao.getServiceById(offeredDto.getService().getId()));
            agentServiceOffered.setAmount(offeredDto.getAmount());
            agentServiceOffered.setEducationAgent(educationAgent);
            agentServiceOffered.setCreatedBy(educationAgentDto.getCreatedBy());
            agentServiceOffered.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
            agentServiceOffered.setUpdatedBy(educationAgentDto.getUpdatedBy());
            agentServiceOffered.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
            agentServiceOffered.setDeletedBy(educationAgentDto.getDeletedBy());
            agentServiceOffered.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
            educationAgentDao.saveAgentServiceOffered(agentServiceOffered);
        }
    }

    public void saveAgentEducationDetails(EducationAgentDto educationAgentDto, EducationAgent educationAgent) {
        educationAgentDao.deleteAgentEducationDetail(educationAgent.getId());
        for (AgentEducationDetailDto educationDetailDto : educationAgentDto.getAgentEducationDetails()) {
            AgentEducationDetail agentEducationDetail = new AgentEducationDetail();
            agentEducationDetail.setCourse(educationDetailDto.getCourse());
            agentEducationDetail.setEducationAgent(educationAgent);
            agentEducationDetail.setInstitute(educationDetailDto.getInstitute());
            agentEducationDetail.setDurationFrom(educationDetailDto.getDurationFrom());
            agentEducationDetail.setDurationTo(educationDetailDto.getDurationTo());
            agentEducationDetail.setCreatedBy(educationAgentDto.getCreatedBy());
            agentEducationDetail.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
            agentEducationDetail.setUpdatedBy(educationAgentDto.getUpdatedBy());
            agentEducationDetail.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
            agentEducationDetail.setDeletedBy(educationAgentDto.getDeletedBy());
            agentEducationDetail.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
            educationAgentDao.saveAgentEducationDetail(agentEducationDetail);
        }
    }

    public void saveAgentMediaDocumentation(EducationAgentDto educationAgentDto, EducationAgent educationAgent) {
        educationAgentDao.deleteAgentMediaDocumentation(educationAgent.getId());
        for (AgentMediaDocumentationDto agentMediaDocumentation : educationAgentDto.getAgentMediaDocumentations()) {
            AgentMediaDocumentation agentMediaDocumentation2 = new AgentMediaDocumentation();
            agentMediaDocumentation2.setDocumentName(agentMediaDocumentation.getDocumentName());
            agentMediaDocumentation2.setEducationAgent(educationAgent);
            agentMediaDocumentation2.setType(agentMediaDocumentation.getType());
            agentMediaDocumentation2.setCreatedBy(educationAgentDto.getCreatedBy());
            agentMediaDocumentation2.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
            agentMediaDocumentation2.setUpdatedBy(educationAgentDto.getUpdatedBy());
            agentMediaDocumentation2.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
            agentMediaDocumentation2.setDeletedBy(educationAgentDto.getDeletedBy());
            agentMediaDocumentation2.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
            educationAgentDao.saveAgentMediaDocumentation(agentMediaDocumentation2);
        }
    }

}
