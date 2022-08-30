package com.yuzee.app.processor;

import java.util.*;
import java.util.stream.Collectors;

import com.yuzee.app.dao.InstituteEnglishRequirementDao;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteEnglishRequirements;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.InstituteEnglishRequirementsDto;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.util.ObjectUtils;

@Service
@CommonsLog
public class InstituteEnglishRequirementsProcessor {
	
	@Autowired
	private InstituteDao instituteDAO;

	@Autowired
	private InstituteEnglishRequirementDao instituteEnglishRequirementsDao;

	@Autowired
	private MessageTranslator messageTranslator;
	
	public InstituteEnglishRequirements addInstituteEnglishRequirements(String userId, String instituteId, InstituteEnglishRequirementsDto instituteEnglishRequirementsDto) throws Exception {
		log.debug("Inside InstituteEnglishRequirementsDao method()");
		// TODO validate userId have access for institute Id
		log.info("Getting institute having institute id "+instituteId);
		Optional<Institute> sourceInstituteFromDB = instituteDAO.getInstituteByInstituteId(UUID.fromString(instituteId));
		if (sourceInstituteFromDB.isEmpty()) {
			log.error(messageTranslator.toLocale("institute_info.id.notFound",instituteId,Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("institute_info.id.notFound",instituteId));
		}
		log.info("Adding institute english requirements with name "+instituteEnglishRequirementsDto.getExamName());
		InstituteEnglishRequirements instituteEnglishRequirements = new InstituteEnglishRequirements(UUID.randomUUID().toString(), instituteEnglishRequirementsDto.getExamName(),
				instituteEnglishRequirementsDto.getReadingMarks(), instituteEnglishRequirementsDto.getListeningMarks(), instituteEnglishRequirementsDto.getWritingMarks(), instituteEnglishRequirementsDto.getOralMarks());
		log.info("Persisting institute english requirements in DB");
		return instituteEnglishRequirementsDao.addUpdateInstituteEnglishRequirements(instituteEnglishRequirements);
	}
	
	public InstituteEnglishRequirements updateInstituteEnglishRequirements (String userId, String englishRequirementId, InstituteEnglishRequirementsDto instituteEnglishRequirementsDto) throws Exception {
		log.debug("Inside updateInstituteEnglishRequirements method()");
		// TODO validate userId have access for institute Id
		
		  log.info("Getting institute english requirements having requirement id  "
		  +englishRequirementId); Institute institute =
		  instituteDAO.get(UUID.fromString(instituteEnglishRequirementsDto.getInstituteId()));
		  Optional<InstituteEnglishRequirements> optionalInstituteEnglishRequirements;
		  InstituteEnglishRequirements instituteEnglishRequirements = new
	  InstituteEnglishRequirements(); 
		  if (ObjectUtils.isEmpty(institute)) {
		  log.error(messageTranslator.toLocale("institute_requirement.id.notFound",
		  englishRequirementId,Locale.US)); throw new
		  NotFoundException(messageTranslator.toLocale(
		 "institute_requirement.id.notFound",instituteEnglishRequirementsDto.getInstituteId())); } log.
		  info("Getting institute id from InstituteEnglishRequirements and validate it with user id"
		 );
		 
		// TODO validate userId have access for institute Id

		instituteEnglishRequirements =  instituteEnglishRequirementsDao.getInstituteEnglishRequirementsById(UUID.fromString(englishRequirementId));
		if(ObjectUtils.isEmpty(instituteEnglishRequirements)){
			log.error("no englishRequirements found for id : {} " + englishRequirementId);
			throw  new NotFoundException("no englishRequirements found for id : {}" + englishRequirementId);
		}

		log.info("updating institute english requirements with id " + englishRequirementId);
		if(!ObjectUtils.isEmpty(instituteEnglishRequirements)) {
			instituteEnglishRequirements.setExamName(instituteEnglishRequirementsDto.getExamName());
			instituteEnglishRequirements.setListeningMarks(instituteEnglishRequirementsDto.getListeningMarks());
			instituteEnglishRequirements.setOralMarks(instituteEnglishRequirementsDto.getOralMarks());
			instituteEnglishRequirements.setReadingMarks(instituteEnglishRequirementsDto.getReadingMarks());
			instituteEnglishRequirements.setWritingMarks(instituteEnglishRequirementsDto.getWritingMarks());
			log.info("Persisting institute english requirements in DB");
		}
		return instituteEnglishRequirementsDao.addUpdateInstituteEnglishRequirements(instituteEnglishRequirements);
	}
	
	public List<InstituteEnglishRequirementsDto> getListOfInstituteEnglishRequirements (String userId, String instituteEnglishRequirementsId,String caller) {
		

		List<InstituteEnglishRequirementsDto> listOfInstituteEnglishRequirementsResponseDto = new ArrayList<InstituteEnglishRequirementsDto>();
	
		if (caller.equalsIgnoreCase("PRIVATE")) {
			//TODO check user id have appropriated access for institute id
		}
		
		log.info("Getting list of institute english requirements for institute id "+instituteEnglishRequirementsId);
		List<InstituteEnglishRequirements> listOfInstituteEnglishRequirements = instituteEnglishRequirementsDao.getListInstituteEnglishRequirementsById(UUID.fromString(instituteEnglishRequirementsId));
		if (!CollectionUtils.isEmpty(listOfInstituteEnglishRequirements)) {
			listOfInstituteEnglishRequirements.forEach(instituteEnglishQualification -> {
				InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto
						(instituteEnglishQualification.getExamName(), instituteEnglishQualification.getReadingMarks()
								, instituteEnglishQualification.getListeningMarks(),
						instituteEnglishQualification.getWritingMarks(), instituteEnglishQualification.getOralMarks());
				listOfInstituteEnglishRequirementsResponseDto.add(instituteEnglishRequirementsDto);
			});
		}
	
		return listOfInstituteEnglishRequirementsResponseDto;	
	}
	
	public void deleteInstituteEnglishRequirements(String userId, String englishRequirementId) {
		log.debug("Inside deleteInstituteEnglishRequirements method()");
		// TODO validate userId have access for institute Id
		log.info("Getting institute english requirements having requirement id  " + englishRequirementId);
		InstituteEnglishRequirements instituteEnglishRequirements = instituteEnglishRequirementsDao.getInstituteEnglishRequirementsById(UUID.fromString(englishRequirementId));
		if (ObjectUtils.isEmpty(instituteEnglishRequirements)) {
			log.error("no EnglishRequirement found for englishRequirementId {} : " + englishRequirementId);
			throw new NotFoundException("no EnglishRequirement found for englishRequirementId : {} " + englishRequirementId);
		}
			log.info("Deleting institute english requirement by Id " + englishRequirementId);
			instituteEnglishRequirementsDao.deleteInstituteEnglishRequirementsById(UUID.fromString(englishRequirementId));
	}
}
