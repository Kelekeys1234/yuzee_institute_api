package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.seeka.app.bean.AgentEducationDetail;
import com.seeka.app.bean.AgentMediaDocumentation;
import com.seeka.app.bean.AgentServiceOffered;
import com.seeka.app.bean.EducationAgent;
import com.seeka.app.bean.EducationAgentAccomplishment;
import com.seeka.app.bean.EducationAgentPartnerships;
import com.seeka.app.bean.EducationAgentSkill;
import com.seeka.app.bean.Skill;
import com.seeka.app.dao.ICityDAO;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dao.IEducationAgentDAO;
import com.seeka.app.dao.IInstituteDAO;
import com.seeka.app.dao.IServiceDetailsDAO;
import com.seeka.app.dto.AgentEducationDetailDto;
import com.seeka.app.dto.AgentMediaDocumentationDto;
import com.seeka.app.dto.AgentServiceOfferedDto;
import com.seeka.app.dto.EducationAgentDto;
import com.seeka.app.dto.EducationAgentGetAllDto;
import com.seeka.app.dto.EducationAgentPartnershipsDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.PaginationUtil;

@Service
public class EducationAgentService implements IEducationAgentService {

    @Autowired
    private IEducationAgentDAO educationAgentDao;

    @Autowired
    private ICountryDAO countryDao;

    @Autowired
    private ICityDAO cityDao;

    @Autowired
    private IServiceDetailsDAO serviceDao;

    @Autowired
    private IInstituteDAO instituteDao;

    @Autowired
    private ICourseDAO courseDao;

    @Override
    public void save(@Valid EducationAgentDto educationAgentDto) {
        EducationAgent educationAgent = convertAgentDtoToBean(educationAgentDto, null);
        educationAgentDao.saveEducationAgent(educationAgent);
        saveAgentDetails(educationAgentDto, educationAgent);
    }

    private EducationAgent convertAgentDtoToBean(@Valid EducationAgentDto educationAgentDto, BigInteger id) {
        EducationAgent educationAgent = null;
        if (id != null) {
            educationAgent = educationAgentDao.get(id);
        } else {
            educationAgent = new EducationAgent();
        }
        educationAgent.setFirstName(educationAgentDto.getFirstName());
        educationAgent.setLastName(educationAgentDto.getLastName());
        educationAgent.setDescription(educationAgentDto.getDescription());
        educationAgent.setCountry(countryDao.get(educationAgentDto.getCountry()));
        educationAgent.setCity(cityDao.get(educationAgentDto.getCity()));
        educationAgent.setCreatedBy(educationAgentDto.getCreatedBy());
        educationAgent.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
        educationAgent.setUpdatedBy(educationAgentDto.getUpdatedBy());
        educationAgent.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
        educationAgent.setPhoneNumber(educationAgentDto.getPhoneNumber());
        educationAgent.setEmail(educationAgentDto.getEmail());
        return educationAgent;
    }

    @Override
    public void update(@Valid EducationAgentDto educationAgentDto, BigInteger id) {
        EducationAgent educationAgent = convertAgentDtoToBean(educationAgentDto, id);
        educationAgentDao.updateEducationAgent(educationAgent);
        saveAgentDetails(educationAgentDto, educationAgent);
    }

    private void saveAgentDetails(@Valid EducationAgentDto educationAgentDto, EducationAgent educationAgent) {
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
        if (educationAgentDto.getAccomplishment() != null && !educationAgentDto.getAccomplishment().isEmpty()) {
            saveEducationAgentAccomplishment(educationAgentDto, educationAgent);
        }
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
            agentServiceOffered.setCountry(educationAgentDto.getCountry());
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

    public void saveEducationAgentAccomplishment(EducationAgentDto educationAgentDto, EducationAgent educationAgent) {
        educationAgentDao.deleteEducationAgentAccomplishment(educationAgent.getId());
        for (String accomplishment : educationAgentDto.getAccomplishment()) {
            EducationAgentAccomplishment educationAgentAccomplishment = new EducationAgentAccomplishment();
            educationAgentAccomplishment.setAccomplishment(accomplishment);
            educationAgentAccomplishment.setEducationAgent(educationAgent);
            educationAgentDao.saveEducationAgentAccomplishment(educationAgentAccomplishment);
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

    @Override
    public Map<String, Object> getAllEducationAgent(Integer pageNumber, Integer pageSize) {
        Map<String, Object> response = new HashMap<>();
        List<EducationAgentGetAllDto> educationAgents = null;
        int totalCount = 0;
        PaginationUtilDto paginationUtilDto = null;
        try {
            totalCount = educationAgentDao.findTotalCount();
            int startIndex = (pageNumber - 1) * pageSize;
            paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
            educationAgents = educationAgentDao.getAll(startIndex, pageSize);
            if (educationAgents != null && !educationAgents.isEmpty()) {
                response.put("message", "Education agent fetched successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Education agent not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", educationAgents);
        response.put("totalCount", totalCount);
        response.put("pageNumber", paginationUtilDto.getPageNumber());
        response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
        response.put("hasNextPage", paginationUtilDto.isHasNextPage());
        response.put("totalPages", paginationUtilDto.getTotalPages());
        return response;
    }

    @Override
    public Map<String, Object> get(@Valid BigInteger id) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            EducationAgent educationAgent = educationAgentDao.get(id);
            if (educationAgent == null) {
                response.put("message", "Education agent not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            } else {
                EducationAgentDto agentDto = null;
                if (educationAgent != null) {
                    agentDto = convertEductaionAgentToDto(educationAgent);
                }
                List<EducationAgentSkill> agentSkills = educationAgentDao.fetchEducationAgentSkillByEducationAgentId(id);
                List<Skill> skills = new ArrayList<>();
                for (EducationAgentSkill educationAgentSkill : agentSkills) {
                    Skill skill = educationAgentDao.fetchSkill(educationAgentSkill.getSkillId().getId());
                    skills.add(skill);
                }
                agentDto.setSkill(skills);

                List<AgentServiceOffered> agentServiceOffereds = educationAgentDao.fetchAgentServiceOffered(id);
                if (agentServiceOffereds != null && !agentServiceOffereds.isEmpty()) {
                    List<AgentServiceOfferedDto> agentServiceOfferedDtos = new ArrayList<>();
                    for (AgentServiceOffered offered : agentServiceOffereds) {
                        AgentServiceOfferedDto agentServiceOfferedDto = convertAgentServiceOfferedToDto(offered);
                        agentServiceOfferedDtos.add(agentServiceOfferedDto);
                    }
                    agentDto.setAgentServiceOffereds(agentServiceOfferedDtos);
                }
                List<AgentEducationDetail> agentEducationDetails = educationAgentDao.fetchAgentEducationDetail(id);
                if (agentEducationDetails != null && !agentEducationDetails.isEmpty()) {
                    List<AgentEducationDetailDto> agentEducationDetailDtos = new ArrayList<>();
                    for (AgentEducationDetail agentEducationDetail : agentEducationDetails) {
                        AgentEducationDetailDto agentEducationDetailDto = convertAgentEducationDetailToDto(agentEducationDetail);
                        agentEducationDetailDtos.add(agentEducationDetailDto);
                    }
                    agentDto.setAgentEducationDetails(agentEducationDetailDtos);
                }
                List<AgentMediaDocumentation> agentMediaDocumentations = educationAgentDao.fetchAgentMediaDocumentation(id);
                if (agentMediaDocumentations != null && !agentMediaDocumentations.isEmpty()) {
                    List<AgentMediaDocumentationDto> agentMediaDocumentationDtos = new ArrayList<>();
                    for (AgentMediaDocumentation agentMediaDocumentation : agentMediaDocumentations) {
                        AgentMediaDocumentationDto agentMediaDocumentationDto = convertAgentMediaDocumentationToDto(agentMediaDocumentation);
                        agentMediaDocumentationDtos.add(agentMediaDocumentationDto);
                    }
                    agentDto.setAgentMediaDocumentations(agentMediaDocumentationDtos);
                }
                List<EducationAgentAccomplishment> agentAccomplishments = educationAgentDao.fetcheducationAgentAccomplishment(id);
                if (agentAccomplishments != null && !agentAccomplishments.isEmpty()) {
                    List<String> accomplishmentList = new ArrayList<>();
                    for (EducationAgentAccomplishment accomplishment : agentAccomplishments) {
                        accomplishmentList.add(accomplishment.getAccomplishment());
                    }
                    agentDto.setAccomplishment(accomplishmentList);
                }

                response.put("message", "Education agent fetched successfully");
                response.put("status", HttpStatus.OK.value());
                response.put("data", agentDto);
            }
        } catch (Exception ex) {
            response.put("message", ex.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    public EducationAgentDto convertEductaionAgentToDto(EducationAgent agent) {
        EducationAgentDto agentDto = new EducationAgentDto();
        agentDto.setFirstName(agent.getFirstName());
        agentDto.setLastName(agent.getLastName());
        agentDto.setDescription(agent.getDescription());
        agentDto.setCity(agent.getCity().getId());
        agentDto.setCountry(agent.getCountry().getId());
        agentDto.setPhoneNumber(agent.getPhoneNumber());
        agentDto.setEmail(agent.getEmail());
        agentDto.setCreatedBy(agent.getCreatedBy());
        agentDto.setUpdatedBy(agent.getUpdatedBy());
        agentDto.setDeletedBy(agent.getDeletedBy());
        return agentDto;
    }

    public AgentServiceOfferedDto convertAgentServiceOfferedToDto(AgentServiceOffered offered) {
        AgentServiceOfferedDto agentServiceOfferedDto = new AgentServiceOfferedDto();
        agentServiceOfferedDto.setAmount(offered.getAmount());
        agentServiceOfferedDto.setService(offered.getService());
        agentServiceOfferedDto.setCountry(offered.getCountry());
        return agentServiceOfferedDto;
    }

    public AgentEducationDetailDto convertAgentEducationDetailToDto(AgentEducationDetail agentEducationDetail) {
        AgentEducationDetailDto agentEducationDetailDto = new AgentEducationDetailDto();
        agentEducationDetailDto.setCourse(agentEducationDetail.getCourse());
        agentEducationDetailDto.setDurationFrom(agentEducationDetail.getDurationFrom());
        agentEducationDetailDto.setDurationTo(agentEducationDetail.getDurationTo());
        agentEducationDetailDto.setInstitute(agentEducationDetail.getInstitute());
        return agentEducationDetailDto;
    }

    public AgentMediaDocumentationDto convertAgentMediaDocumentationToDto(AgentMediaDocumentation agentMediaDocumentation) {
        AgentMediaDocumentationDto agentMediaDocumentationDto = new AgentMediaDocumentationDto();
        agentMediaDocumentationDto.setDocumentName(agentMediaDocumentation.getDocumentName());
        agentMediaDocumentationDto.setType(agentMediaDocumentation.getType());
        return agentMediaDocumentationDto;
    }

    @Override
    public void savePartnership(EducationAgentPartnershipsDto agentPartnershipsDto) {
        try {
            convertDtoToEducationAgentPartnerships(agentPartnershipsDto);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void convertDtoToEducationAgentPartnerships(EducationAgentPartnershipsDto agentPartnershipsDto) {
        try {
            Integer i = 0;
            educationAgentDao.deleteEducationAgentPartnerships(agentPartnershipsDto.getEducationAgentId());
            for (BigInteger institute : agentPartnershipsDto.getInstituteId()) {
                EducationAgentPartnerships agentPartnership = new EducationAgentPartnerships();
                agentPartnership.setEducationAgent(educationAgentDao.get(agentPartnershipsDto.getEducationAgentId()));
                agentPartnership.setInstitute(instituteDao.get(institute));
                agentPartnership.setCourse(courseDao.get(agentPartnershipsDto.getCourseId().get(i)));
                agentPartnership.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
                agentPartnership.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                agentPartnership.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
                agentPartnership.setCreatedBy(agentPartnershipsDto.getCreatedBy());
                agentPartnership.setUpdatedBy(agentPartnershipsDto.getUpdatedBy());
                agentPartnership.setCountry(countryDao.get(agentPartnershipsDto.getCountry()));
                educationAgentDao.saveEducationAgentPartnerships(agentPartnership);
                i++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> deleteEducationAgent(BigInteger id) {
        Map<String, Object> response = new HashMap<>();
        try {
            EducationAgent educationAgent = educationAgentDao.get(id);
            if (educationAgent != null) {
                educationAgent.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
                educationAgentDao.updateEducationAgent(educationAgent);
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Education Agent Delete Successfully");
            } else {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Education Agent not found");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        return response;
    }

    @Override
    public Map<String, Object> getAllSkill() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Skill> skills = educationAgentDao.getAllSkill();
            if (skills != null) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Skill retrieve successfully");
                response.put("data", skills);
            } else {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Skill not found");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        return response;
    }
}
