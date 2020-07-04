package com.seeka.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.AgentEducationDetail;
import com.seeka.app.bean.AgentMediaDocumentation;
import com.seeka.app.bean.AgentServiceOffered;
import com.seeka.app.bean.EducationAgent;
import com.seeka.app.bean.EducationAgentAccomplishment;
import com.seeka.app.bean.EducationAgentPartnerships;
import com.seeka.app.bean.EducationAgentSkill;
import com.seeka.app.dao.CourseDao;
import com.seeka.app.dao.EducationAgentDao;
import com.seeka.app.dao.ServiceDetailsDao;
import com.seeka.app.dao.InstituteDao;
import com.seeka.app.dto.AgentEducationDetailDto;
import com.seeka.app.dto.AgentMediaDocumentationDto;
import com.seeka.app.dto.AgentServiceOfferedDto;
import com.seeka.app.dto.EducationAgentDto;
import com.seeka.app.dto.EducationAgentGetAllDto;
import com.seeka.app.dto.EducationAgentPartnershipsDto;
import com.seeka.app.dto.PaginationResponseDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.PaginationUtil;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class EducationAgentProcessor {

    @Autowired
    private EducationAgentDao educationAgentDao;

    @Autowired
    private ServiceDetailsDao serviceDao;

    @Autowired
    private InstituteDao instituteDao;

    @Autowired
    private CourseDao courseDao;

    public void saveEducationAgent(EducationAgentDto educationAgentDto) {
    	log.debug("Inside saveEducationAgent() method");
    	EducationAgent educationAgent = convertAgentDtoToBean(educationAgentDto, null);
    	log.info("Calling DAO layer to save education agent in DB");
        educationAgentDao.saveEducationAgent(educationAgent);
        saveAgentDetails(educationAgentDto, educationAgent);
    }

    private EducationAgent convertAgentDtoToBean(EducationAgentDto educationAgentDto, String id) {
    	log.info("Converting educationAgent DTO to Bean class");
    	EducationAgent educationAgent = null;
        if (id != null) {
        	log.info("Education Agent Id is not null, hence going to fetch educationAgent from DB having id = "+id);
            educationAgent = educationAgentDao.get(id);
            educationAgent.setUpdatedBy("API");
            educationAgent.setUpdatedOn(new Date());
        } else {
        	log.info("AgentId is null, hence creating object of education agent");
            educationAgent = new EducationAgent();
        }
        log.info("Adding education agent values in bean class object");
        educationAgent.setFirstName(educationAgentDto.getFirstName());
        educationAgent.setLastName(educationAgentDto.getLastName());
        educationAgent.setDescription(educationAgentDto.getDescription());
        educationAgent.setCountryName(educationAgentDto.getCountry());
        educationAgent.setCityName(educationAgentDto.getCity());
        educationAgent.setCreatedBy("API");
        educationAgent.setCreatedOn(new Date());
        educationAgent.setPhoneNumber(educationAgentDto.getPhoneNumber());
        educationAgent.setEmail(educationAgentDto.getEmail());
        return educationAgent;
    }

    public void updateEducationAgent(EducationAgentDto educationAgentDto, String id) {
    	log.debug("Inside updateEducationAgent() method");
    	EducationAgent educationAgent = convertAgentDtoToBean(educationAgentDto, id);
    	log.info("Calling DAO layer to update existing education agent in DB");
    	educationAgentDao.updateEducationAgent(educationAgent);
        saveAgentDetails(educationAgentDto, educationAgent);
    }

    private void saveAgentDetails(EducationAgentDto educationAgentDto, EducationAgent educationAgent) {
        log.info("Going to save other education agent details");
    	if (educationAgentDto.getSkill() != null && !educationAgentDto.getSkill().isEmpty()) {
    		log.info("Education agent skill is not null hence saving skill in DB");
            saveSkillAndEducationAgentSkill(educationAgentDto, educationAgent);
        }
        if (educationAgentDto.getAgentServiceOffereds() != null && !educationAgentDto.getAgentServiceOffereds().isEmpty()) {
        	log.info("Agent Service offered is not null hence saving services in DB");
            saveAgentServiceOffered(educationAgentDto, educationAgent);
        }
        if (educationAgentDto.getAgentEducationDetails() != null && !educationAgentDto.getAgentEducationDetails().isEmpty()) {
        	log.info("Agent Education details is not null hence saving education details in DB");
        	saveAgentEducationDetails(educationAgentDto, educationAgent);
        }
        if (educationAgentDto.getAccomplishment() != null && !educationAgentDto.getAccomplishment().isEmpty()) {
        	log.info("Education agent accomplishment is not null hence saving accomplishment in DB");
            saveEducationAgentAccomplishment(educationAgentDto, educationAgent);
        }
    }

    public void saveSkillAndEducationAgentSkill(EducationAgentDto educationAgentDto, EducationAgent educationAgent) {
    	log.debug("Inside saveSkillAndEducationAgentSkill() method");
    	log.info("Going to delete existing education agent skill in DB having agentId = "+educationAgent.getId());
    	educationAgentDao.deleteEducationAgentSkill(educationAgent.getId());
        log.info("Start iterating education agent skills coming in request");
        educationAgentDto.getSkill().stream().forEach(skill -> {
            EducationAgentSkill educationAgentSkill = new EducationAgentSkill();
            educationAgentSkill.setSkill(skill);
            educationAgentSkill.setEducationAgent(educationAgent);
            educationAgentSkill.setCreatedBy("API");
            educationAgentSkill.setCreatedOn(new Date());
            log.info("Calling DAO layer to save education agent skills in DB");
            educationAgentDao.saveEducationAgentSkill(educationAgentSkill);
        
        });
    }

    public void saveAgentServiceOffered(EducationAgentDto educationAgentDto, EducationAgent educationAgent) {
    	log.debug("Inside saveAgentServiceOffered() method");
    	log.info("Going to delete existing agent service offered in DB having agentId = "+educationAgent.getId());
    	educationAgentDao.deleteAgentServiceOffered(educationAgent.getId());
        for (AgentServiceOfferedDto offeredDto : educationAgentDto.getAgentServiceOffereds()) {
            AgentServiceOffered agentServiceOffered = new AgentServiceOffered();
            agentServiceOffered.setService(serviceDao.getServiceById(offeredDto.getService()));
            agentServiceOffered.setAmount(offeredDto.getAmount());
            agentServiceOffered.setEducationAgent(educationAgent);
            agentServiceOffered.setCreatedBy("API");
            agentServiceOffered.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
            agentServiceOffered.setCurrency(offeredDto.getCurrency());
            log.info("Calling DAO layer to save agent service offered in DB");
            educationAgentDao.saveAgentServiceOffered(agentServiceOffered);
        }
    }

    public void saveAgentEducationDetails(EducationAgentDto educationAgentDto, EducationAgent educationAgent) {
    	log.debug("Inside saveAgentEducationDetails() method");
    	log.info("Going to delete existing agent education details in DB having agentId = "+educationAgent.getId());
    	educationAgentDao.deleteAgentEducationDetail(educationAgent.getId());
        for (AgentEducationDetailDto educationDetailDto : educationAgentDto.getAgentEducationDetails()) {
            AgentEducationDetail agentEducationDetail = new AgentEducationDetail();
            agentEducationDetail.setEducationAgent(educationAgent);
            
            log.info("Fetching institute data having instituteId = "+educationDetailDto.getInstituteId());
            agentEducationDetail.setInstitute(instituteDao.get(educationDetailDto.getInstituteId()));
			
            log.info("Fetching course data having courseId = "+educationDetailDto.getCourseId());
			agentEducationDetail.setCourse(courseDao.get(educationDetailDto.getCourseId()));
            
			agentEducationDetail.setDurationFrom(educationDetailDto.getDurationFrom());
            agentEducationDetail.setDurationTo(educationDetailDto.getDurationTo());
            agentEducationDetail.setCreatedBy("API");
            agentEducationDetail.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
            agentEducationDetail.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
            log.info("Calling DAO layer to save agent education details in DB");
            educationAgentDao.saveAgentEducationDetail(agentEducationDetail);
        }
    }

    public void saveEducationAgentAccomplishment(EducationAgentDto educationAgentDto, EducationAgent educationAgent) {
    	log.debug("Inside saveEducationAgentAccomplishment() method");
    	log.info("Going to delete existing education agent accomplishments in DB having agentId = "+educationAgent.getId());
    	educationAgentDao.deleteEducationAgentAccomplishment(educationAgent.getId());
        for (String accomplishment : educationAgentDto.getAccomplishment()) {
            EducationAgentAccomplishment educationAgentAccomplishment = new EducationAgentAccomplishment();
            educationAgentAccomplishment.setAccomplishment(accomplishment);
            educationAgentAccomplishment.setEducationAgent(educationAgent);
            log.info("Calling DAO layer to save agent education details in DB");
            educationAgentDao.saveEducationAgentAccomplishment(educationAgentAccomplishment);
        }
    }

    public PaginationResponseDto getAllEducationAgent(Integer pageNumber, Integer pageSize) {
    	log.debug("Inside getAllEducationAgent() method");
    	PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
    	try {
    		log.info("Fetching total count of educationAgent from DB");
            int totalCount = educationAgentDao.findTotalCount();
            int startIndex = (pageNumber - 1) * pageSize;
            log.info("Fetching educationAgent from DB based on startIndex and pageSize");
            List<EducationAgentGetAllDto> educationAgents = educationAgentDao.getAll(startIndex, pageSize);
            if (!CollectionUtils.isEmpty(educationAgents)) {
            	log.info("Education Agent details fetched from DB, hence adding values in response");
            	PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
            	paginationResponseDto.setResponse(educationAgents);
        		paginationResponseDto.setTotalCount(totalCount);
        		paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
        		paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
        		paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
        		paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
            }
        } catch (Exception exception) {
        	log.error("Exception while fetching Education Agent having exception = " + exception);
        }
        return paginationResponseDto;
    }

	public EducationAgentDto getEducationAgent(@Valid String id) throws NotFoundException {
		log.debug("Inside getEducationAgent() method");
		EducationAgentDto agentDto = new EducationAgentDto();
		log.info("Fetching EducationAgent from DB for agentId = " + id);
		EducationAgent educationAgent = educationAgentDao.get(id);
		if (educationAgent == null) {
			log.error("No EducationAgent Detail found in DB for agentId = " + id);
			throw new NotFoundException("No EducationAgent Detail found in DB for agentId = " + id);
		} else {
			log.info("Education Agent found in DB, hence converting Bena class to DTO class");
			agentDto = convertEductaionAgentToDto(educationAgent);
			log.info("Fetching Education agent skills from DB having agentId = " + id);
			List<EducationAgentSkill> agentSkills = educationAgentDao.fetchEducationAgentSkillByEducationAgentId(id);
			List<String> skills = new ArrayList<>();
			agentSkills.stream().forEach(educationAgentSkill -> {
				skills.add(educationAgentSkill.getSkill());
			});
			agentDto.setSkill(skills);
			log.info("Fetching Agent Services Offereds from DB having agentId = " + id);
			List<AgentServiceOffered> agentServiceOffereds = educationAgentDao.fetchAgentServiceOffered(id);
			if (!CollectionUtils.isEmpty(agentServiceOffereds)) {
				log.info("Agent Service Offered found in DB, hence start iterating data");
				List<AgentServiceOfferedDto> agentServiceOfferedDtos = new ArrayList<>();
				for (AgentServiceOffered offered : agentServiceOffereds) {
					AgentServiceOfferedDto agentServiceOfferedDto = convertAgentServiceOfferedToDto(offered);
					agentServiceOfferedDtos.add(agentServiceOfferedDto);
				}
				agentDto.setAgentServiceOffereds(agentServiceOfferedDtos);
			}
			log.info("Fetching Agent Education Details from DB having agentId = " + id);
			List<AgentEducationDetail> agentEducationDetails = educationAgentDao.fetchAgentEducationDetail(id);
			if (!CollectionUtils.isEmpty(agentEducationDetails)) {
				log.info("Agent Education Detail found in DB, hence start iterating data");
				List<AgentEducationDetailDto> agentEducationDetailDtos = new ArrayList<>();
				for (AgentEducationDetail agentEducationDetail : agentEducationDetails) {
					AgentEducationDetailDto agentEducationDetailDto = convertAgentEducationDetailToDto(agentEducationDetail);
					agentEducationDetailDtos.add(agentEducationDetailDto);
				}
				agentDto.setAgentEducationDetails(agentEducationDetailDtos);
			}
			log.info("Fetching Education Agent Accomplishment from DB having agentId = " + id);
			List<EducationAgentAccomplishment> agentAccomplishments = educationAgentDao.fetcheducationAgentAccomplishment(id);
			if (!CollectionUtils.isEmpty(agentAccomplishments)) {
				log.info("Education Agent Accomplishment found in DB, hence start iterating data");
				List<String> accomplishmentList = new ArrayList<>();
				for (EducationAgentAccomplishment accomplishment : agentAccomplishments) {
					accomplishmentList.add(accomplishment.getAccomplishment());
				}
				agentDto.setAccomplishment(accomplishmentList);
			}
		}
		return agentDto;
	}

    public EducationAgentDto convertEductaionAgentToDto(EducationAgent agent) {
        EducationAgentDto agentDto = new EducationAgentDto();
        agentDto.setFirstName(agent.getFirstName());
        agentDto.setLastName(agent.getLastName());
        agentDto.setDescription(agent.getDescription());
        agentDto.setCity(agent.getCityName());
        agentDto.setCountry(agent.getCountryName());
        agentDto.setPhoneNumber(agent.getPhoneNumber());
        agentDto.setEmail(agent.getEmail());
        return agentDto;
    }

    public AgentServiceOfferedDto convertAgentServiceOfferedToDto(AgentServiceOffered offered) {
        AgentServiceOfferedDto agentServiceOfferedDto = new AgentServiceOfferedDto();
        agentServiceOfferedDto.setAmount(offered.getAmount());
        if (offered.getService() != null) {
            agentServiceOfferedDto.setService(offered.getService().getId());
        }
        agentServiceOfferedDto.setCurrency(offered.getCurrency());
        return agentServiceOfferedDto;
    }

    public AgentEducationDetailDto convertAgentEducationDetailToDto(AgentEducationDetail agentEducationDetail) {
        AgentEducationDetailDto agentEducationDetailDto = new AgentEducationDetailDto();
        
        log.info("Fetching institute data having instituteId = "+agentEducationDetail.getInstitute().getId());
        agentEducationDetailDto.setInstituteId(agentEducationDetail.getInstitute().getId());
		
        log.info("Fetching course data having courseId = "+agentEducationDetail.getCourse().getId());
		agentEducationDetailDto.setCourseId(agentEducationDetail.getCourse().getId());
        
        agentEducationDetailDto.setDurationFrom(agentEducationDetail.getDurationFrom());
        agentEducationDetailDto.setDurationTo(agentEducationDetail.getDurationTo());
        return agentEducationDetailDto;
    }

    public AgentMediaDocumentationDto convertAgentMediaDocumentationToDto(AgentMediaDocumentation agentMediaDocumentation) {
        AgentMediaDocumentationDto agentMediaDocumentationDto = new AgentMediaDocumentationDto();
        agentMediaDocumentationDto.setDocumentName(agentMediaDocumentation.getDocumentName());
        agentMediaDocumentationDto.setType(agentMediaDocumentation.getType());
        return agentMediaDocumentationDto;
    }

    public void savePartnership(List<EducationAgentPartnershipsDto> agentPartnershipsDto) {
        log.debug("Inside savePartnership() method");
    	try {
            convertDtoToEducationAgentPartnerships(agentPartnershipsDto);
        } catch (Exception exception) {
            log.error("Exception while saving Partnership having exception = "+exception);
        }
    }

	public void convertDtoToEducationAgentPartnerships(List<EducationAgentPartnershipsDto> agentPartnershipsDtos) {
		try {
			log.info("Start Converting Education Agent Partnership bean to DTO class");
			if (!CollectionUtils.isEmpty(agentPartnershipsDtos)) {
				log.info("Education Agent Partnership is not null hence start deleting exiting partnership data");
				educationAgentDao.deleteEducationAgentPartnerships(agentPartnershipsDtos.get(0).getAgentId());
				log.info("Start iterating agent partnership data and start saving data");
				agentPartnershipsDtos.stream().forEach(educationAgentPartnershipsDto -> {
					EducationAgentPartnerships agentPartnership = new EducationAgentPartnerships();
					log.info("Fetching education agent having agentId = "+educationAgentPartnershipsDto.getAgentId());
					agentPartnership
							.setEducationAgent(educationAgentDao.get(educationAgentPartnershipsDto.getAgentId()));
					log.info("Fetching institute data having instituteId = "+educationAgentPartnershipsDto.getInstituteId());
					agentPartnership.setInstitute(instituteDao.get(educationAgentPartnershipsDto.getInstituteId()));
					log.info("\"Fetching course data having courseId = "+educationAgentPartnershipsDto.getCourseId());
					agentPartnership.setCourse(courseDao.get(educationAgentPartnershipsDto.getCourseId()));
					agentPartnership.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
					agentPartnership.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
					agentPartnership.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
					agentPartnership.setCreatedBy("API");
					agentPartnership.setCountryName(educationAgentPartnershipsDto.getCountryName());
					log.info("Calling DAO layer to save ducation agent partnership in DB");
					educationAgentDao.saveEducationAgentPartnerships(agentPartnership);
				});
			}
		} catch (Exception exception) {
			log.error("Exception while saving partnership data having exception = "+exception);
		}
	}

	public void deleteEducationAgent(String id) throws NotFoundException {
		log.debug("Inside deleteEducationAgent() method");
		log.info("Fetching Education Agent from DB having agentId = "+id);
		EducationAgent educationAgent = educationAgentDao.get(id);
		if (ObjectUtils.isEmpty(educationAgent)) {
			log.error("No Education Agent found in DB for " + id);
			throw new NotFoundException("No Education Agent found in DB for " + id);
		}
		educationAgent.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
		log.info("Calling DAO layer to update Education Agent in DB");
		educationAgentDao.updateEducationAgent(educationAgent);
	}
   }
