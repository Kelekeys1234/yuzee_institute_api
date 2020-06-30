package com.seeka.app.processor;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.controller.handler.StorageHandler;
import com.seeka.app.dao.InstituteDao;
import com.seeka.app.dto.InstituteBasicInfoDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.exception.NotFoundException;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class InstituteBasicInfoProcessor {

	@Autowired
	private InstituteDao iInstituteDAO;
	
	@Autowired
	private StorageHandler storageHandler;
	
	@Transactional(rollbackOn = Throwable.class)
	public void addUpdateInstituteBasicInfo(String userId, String instituteId, InstituteBasicInfoDto instituteBasicInfoDto ) throws Exception {
		log.debug("Inside addUpdateInstituteBasicInfo() method");
		//TODO validate user ID passed in request have access to modify resource
		log.info("Getting institute having institute id "+instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id "+instituteId);
			throw new NotFoundException("No institute found for institute having id "+instituteId);
		}
		Institute institute = instituteFromFb.get();
		log.info("adding updating institute basic info for institute id "+instituteId+ " by user id "+userId);
		institute.setName(instituteBasicInfoDto.getNameOfUniversity());
		institute.setDescription(instituteBasicInfoDto.getDescription());
		log.info("validating institute type Id passed in request");
		InstituteCategoryType instituteCategoryType = iInstituteDAO.getInstituteCategoryType(instituteBasicInfoDto.getInstituteCategoryTypeId());
		
		if (ObjectUtils.isEmpty(instituteCategoryType)) {
			log.error("No institute category found for institute category id "+instituteBasicInfoDto.getInstituteCategoryTypeId());
			throw new NotFoundException("No institute category found for institute category id "+instituteBasicInfoDto.getInstituteCategoryTypeId());
		}
		institute.setInstituteCategoryType(instituteCategoryType);
		log.info("persisting institute having id "+instituteId+ " with updated basic info");
		iInstituteDAO.save(institute);
	}
	
	public InstituteBasicInfoDto getInstituteBasicInfo (String userId , String instituteId, String caller) throws Exception {
		InstituteBasicInfoDto instituteBasicInfoDto = new InstituteBasicInfoDto();
		List<StorageDto> listOfStorageDto = null;
		log.debug("Inside getInstituteBasicInfo() method");
		if (caller.equalsIgnoreCase("PRIVATE")) {
			//TODO validate user ID passed in request have access to modify resource
		}
		log.info("Getting institute having institute id "+instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id "+instituteId);
			throw new NotFoundException("No institute found for institute having id "+instituteId);
		}
		Institute institute = instituteFromFb.get();
		log.info("Getting institute logo for institute id "+instituteId);
		try {
			listOfStorageDto = storageHandler.getCertificates(instituteId, "INSTITUTE_LOGO");
		} catch (Exception e) {
			log.error("Not able to fetch logo for institue id "+instituteId);
		}
		
		if (!CollectionUtils.isEmpty(listOfStorageDto)) {
			log.info("Setting logo URL for institute id "+instituteId);
			instituteBasicInfoDto.setInstituteLogoPath(listOfStorageDto.get(0).getImageURL());
		} 
		instituteBasicInfoDto.setDescription(institute.getDescription());
		instituteBasicInfoDto.setNameOfUniversity(institute.getName());
		if (!ObjectUtils.isEmpty(institute.getInstituteCategoryType())) {
			instituteBasicInfoDto.setInstituteCategoryTypeId(institute.getInstituteCategoryType().getId() );
			instituteBasicInfoDto.setInstituteCategoryTypeName(institute.getInstituteCategoryType().getName());
		}
		return instituteBasicInfoDto;
	}
}
