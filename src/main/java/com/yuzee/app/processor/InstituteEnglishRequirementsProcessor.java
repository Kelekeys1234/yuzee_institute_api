package com.yuzee.app.processor;

import java.util.*;

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

//	@Autowired
//	private InstituteEnglishRequirementsDao instituteEnglishRequirementsDao;

	@Autowired
	private MessageTranslator messageTranslator;
	
	public void addInstituteEnglishRequirements(String userId, String instituteId, InstituteEnglishRequirementsDto instituteEnglishRequirementsDto) throws Exception {
		log.debug("Inside InstituteEnglishRequirementsDao method()");
		// TODO validate userId have access for institute Id
		log.info("Getting institute having institute id "+instituteId);
		Optional<Institute> sourceInstituteFromDB = instituteDAO.getInstituteByInstituteId(UUID.fromString(instituteId));
		if (!sourceInstituteFromDB.isPresent()) {
			log.error(messageTranslator.toLocale("institute_info.id.notfound",instituteId,Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("institute_info.id.notfound",instituteId));
		}
		
		log.info("Adding institute english requirements with name "+instituteEnglishRequirementsDto.getExamName());
		InstituteEnglishRequirements instituteEnglishRequirements = new InstituteEnglishRequirements(instituteId, instituteEnglishRequirementsDto.getExamName(),
				instituteEnglishRequirementsDto.getReadingMarks(), instituteEnglishRequirementsDto.getListeningMarks(), instituteEnglishRequirementsDto.getWritingMarks(), instituteEnglishRequirementsDto.getOralMarks(),
				  new Date(), new Date(), "API", "API");
		log.info("Persisting institute english requirements in DB");
		sourceInstituteFromDB.get().getInstituteEnglishRequirements().add(instituteEnglishRequirements);
		instituteDAO.addUpdateInstitute(sourceInstituteFromDB.get());
	}
	
	public void updateInstituteEnglishRequirements (String userId, String instituteEnglishRequirementsId, InstituteEnglishRequirementsDto instituteEnglishRequirementsDto) throws Exception {
		log.debug("Inside updateInstituteEnglishRequirements method()");
		// TODO validate userId have access for institute Id
		log.info("Getting institute english requirements having requirement id  "+instituteEnglishRequirementsId);
		Institute institute = instituteDAO.get(UUID.fromString(instituteEnglishRequirementsId));
		if (ObjectUtils.isEmpty(institute)) {
			log.error(messageTranslator.toLocale("english_requirement.id.notfound",instituteEnglishRequirementsId,Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("english_requirement.id.notfound",instituteEnglishRequirementsId));
		}
		log.info("Getting institute id from InstituteEnglishRequirements and validate it with user id");
		// TODO validate userId have access for institute Id

		List<InstituteEnglishRequirements> listOfInstituteRequirements =  institute.getInstituteEnglishRequirements();

		log.info("updating institute english requirements with id "+instituteEnglishRequirementsId);
		InstituteEnglishRequirements instituteEnglishRequirements = new InstituteEnglishRequirements();
		instituteEnglishRequirements.setExamName(instituteEnglishRequirementsDto.getExamName());
		instituteEnglishRequirements.setListeningMarks(instituteEnglishRequirementsDto.getListeningMarks());
		instituteEnglishRequirements.setOralMarks(instituteEnglishRequirementsDto.getOralMarks());
		instituteEnglishRequirements.setReadingMarks(instituteEnglishRequirementsDto.getReadingMarks());
		instituteEnglishRequirements.setWritingMarks(instituteEnglishRequirementsDto.getWritingMarks());
		listOfInstituteRequirements.add(instituteEnglishRequirements);
		institute.setInstituteEnglishRequirements(listOfInstituteRequirements);
		log.info("Persisting institute english requirements in DB");
		instituteDAO.addUpdateInstitute(institute);
	}
	
	public List<InstituteEnglishRequirementsDto> getListOfInstituteEnglishRequirements (String userId, String instituteId,String caller) {
		Institute institute = instituteDAO.get(UUID.fromString(instituteId));
		if (ObjectUtils.isEmpty(institute)) {
			log.error(messageTranslator.toLocale("english_requirement.id.notfound",instituteId,Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("english_requirement.id.notfound",instituteId));
		}

		List<InstituteEnglishRequirementsDto> listOfInstituteEnglishRequirementsResponseDto = new ArrayList<InstituteEnglishRequirementsDto>();
		if (caller.equalsIgnoreCase("PRIVATE")) {
			//TODO check user id have appropriated access for institute id
		}
		log.info("Getting list of institute english requirements for institute id "+instituteId);
		List<InstituteEnglishRequirements> listOfInstituteEnglishRequirements = institute.getInstituteEnglishRequirements();
		if (!CollectionUtils.isEmpty(listOfInstituteEnglishRequirements)) {
			listOfInstituteEnglishRequirements.stream().forEach(instituteEnglishQualification -> {
				InstituteEnglishRequirementsDto instituteEnglishRequirementsDto = new InstituteEnglishRequirementsDto(instituteEnglishQualification.toString(), instituteEnglishQualification.getExamName(), instituteEnglishQualification.getReadingMarks(), instituteEnglishQualification.getListeningMarks(),
						instituteEnglishQualification.getWritingMarks(), instituteEnglishQualification.getOralMarks());
				listOfInstituteEnglishRequirementsResponseDto.add(instituteEnglishRequirementsDto);
			});
		}
		return listOfInstituteEnglishRequirementsResponseDto;	
	}
	
	public void deleteInstituteEnglishRequirements(String userId, String instituteEnglishRequirementsId) {
		log.debug("Inside deleteInstituteEnglishRequirements method()");
		// TODO validate userId have access for institute Id
		log.info("Getting institute english requirements having requirement id  " + instituteEnglishRequirementsId);
		Optional<Institute> optionalInstitute = instituteDAO
				.getInsituteEnglishRequirementsById(instituteEnglishRequirementsId);
		if (optionalInstitute.isPresent()) {
			log.info("Deleting institute english requirement by Id " + instituteEnglishRequirementsId);
			instituteDAO.deleteInstituteEnglishRequirementsById(instituteEnglishRequirementsId);
		}
	}
}
