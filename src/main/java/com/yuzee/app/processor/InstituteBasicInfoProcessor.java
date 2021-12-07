package com.yuzee.app.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteCategoryType;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.common.lib.dto.institute.InstituteBasicInfoDto;
import com.yuzee.common.lib.dto.review.ReviewStarDto;
import com.yuzee.common.lib.dto.storage.StorageDto;
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.handler.ReviewHandler;
import com.yuzee.common.lib.handler.StorageHandler;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class InstituteBasicInfoProcessor {

	@Autowired
	private InstituteDao iInstituteDAO;
	
	@Autowired
	private CourseDao courseDAO;
	
	@Autowired
	private ReviewHandler reviewHandler;
	
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
		iInstituteDAO.addUpdateInstitute(institute);
	}
	
	@Transactional
	public InstituteBasicInfoDto getInstituteBasicInfo (String userId , String instituteId, String caller, boolean includeInstituteLogo, boolean includeDetail) throws Exception {
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
		if(includeInstituteLogo) {
			try {
				listOfStorageDto = storageHandler.getStorages(instituteId, EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.LOGO);
			} catch (Exception e) {
				log.error("Not able to fetch logo for institue id "+instituteId);
			}			
		}
		
		if (!CollectionUtils.isEmpty(listOfStorageDto)) {
			log.info("Setting logo URL for institute id "+instituteId);
			instituteBasicInfoDto.setInstituteLogoPath(listOfStorageDto.get(0).getFileURL());
		}
		instituteBasicInfoDto.setDescription(institute.getDescription());
		instituteBasicInfoDto.setNameOfUniversity(institute.getName());
		instituteBasicInfoDto.setCountryName(institute.getCountryName());
		instituteBasicInfoDto.setCityName(institute.getCityName());
		instituteBasicInfoDto.setStateName(institute.getState());
		instituteBasicInfoDto.setAddress(institute.getAddress());
		if (!ObjectUtils.isEmpty(institute.getInstituteCategoryType())) {
			instituteBasicInfoDto.setInstituteCategoryTypeId(institute.getInstituteCategoryType().getId() );
			instituteBasicInfoDto.setInstituteCategoryTypeName(institute.getInstituteCategoryType().getName());
		}
		instituteBasicInfoDto.setCreatedBy(institute.getCreatedBy());
		instituteBasicInfoDto.setWorldRanking(institute.getWorldRanking());
		instituteBasicInfoDto.setDomesticRanking(institute.getDomesticRanking());
		if (includeDetail) {
			instituteBasicInfoDto.setTotalCourses(courseDAO.getTotalCourseCountForInstitute(institute.getId()));
			log.info("Calling review service to fetch user average review for instituteId");
			Map<String, ReviewStarDto> yuzeeReviewMap = reviewHandler.getAverageReview(EntityTypeEnum.INSTITUTE.name(),
					Arrays.asList(instituteId));
			
			ReviewStarDto reviewStarDto = yuzeeReviewMap.get(instituteId);
			if (!ObjectUtils.isEmpty(reviewStarDto)) {
				instituteBasicInfoDto.setStars(reviewStarDto.getReviewStars());
				instituteBasicInfoDto.setReviewsCount(reviewStarDto.getReviewsCount());
			}
		}
		instituteBasicInfoDto.setVerified(institute.isVerified());
		return instituteBasicInfoDto;
	}
}
