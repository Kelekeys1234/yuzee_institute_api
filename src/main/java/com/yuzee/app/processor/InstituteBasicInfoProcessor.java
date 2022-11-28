package com.yuzee.app.processor;

import java.util.*;


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
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class InstituteBasicInfoProcessor {

	@Autowired
	private InstituteDao instituteDAO;
	
	@Autowired
	private CourseDao courseDAO;
	
	@Autowired
	private ReviewHandler reviewHandler;
	
	@Autowired
	private StorageHandler storageHandler;

	@Autowired
	private MessageTranslator messageTranslator;

	public void addUpdateInstituteBasicInfo(String userId, String instituteId, InstituteBasicInfoDto instituteBasicInfoDto ) throws Exception {
		log.debug("Inside addUpdateInstituteBasicInfo() method");
		//TODO validate user ID passed in request have access to modify resource
		log.info("Getting institute having institute id "+instituteId);
		Optional<Institute> instituteFromFb = instituteDAO.getInstituteByInstituteId(UUID.fromString(instituteId));
		if (instituteFromFb.isEmpty()) {
			log.error(messageTranslator.toLocale("institute_info.id.notFound",instituteId,Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("institute_info.id.notFound",instituteId));
		}
		Institute institute = instituteFromFb.get();
		log.info("adding updating institute basic info for institute id "+instituteId+ " by user id "+userId);
		institute.setName(instituteBasicInfoDto.getNameOfUniversity());
		institute.setDescription(instituteBasicInfoDto.getDescription());
		log.info("validating institute type Id passed in request");
		InstituteCategoryType instituteCategoryType = new InstituteCategoryType();
	
		  if
		  (ObjectUtils.isEmpty(instituteBasicInfoDto.getInstituteCategoryTypeName())) {
		  log.error(messageTranslator.toLocale("institute_info.category.id.notFound",
		  instituteBasicInfoDto.getInstituteCategoryTypeName(), Locale.US)); throw new
		  NotFoundException(messageTranslator.toLocale(
		  "institute_info.category.id.notFound",instituteBasicInfoDto.
		  getInstituteCategoryTypeName())); }
		instituteCategoryType.setName(instituteBasicInfoDto.getInstituteCategoryTypeName());
		institute.setInstituteCategoryType(instituteCategoryType);
		log.info("persisting institute having id "+instituteId+ " with updated basic info");
		instituteDAO.addUpdateInstitute(institute);
	}

	public InstituteBasicInfoDto getInstituteBasicInfo (String userId , String instituteId, String caller, boolean includeInstituteLogo, boolean includeDetail) throws Exception {
		InstituteBasicInfoDto instituteBasicInfoDto = new InstituteBasicInfoDto();
		List<StorageDto> listOfStorageDto = null;
		log.debug("Inside getInstituteBasicInfo() method");
		if (caller.equalsIgnoreCase("PRIVATE")) {
			//TODO validate user ID passed in request have access to modify resource
		}
		log.info("Getting institute having institute id "+instituteId);
		Optional<Institute> instituteFromFb = instituteDAO.getInstituteByInstituteId(UUID.fromString(instituteId));
		if (instituteFromFb.isEmpty()) {
			log.error(messageTranslator.toLocale("institute_info.id.notFound",instituteId,Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("institute_info.id.notFound",instituteId));
		}
		Institute institute = instituteFromFb.get();
		log.info("Getting institute logo for institute id "+instituteId);
		if(includeInstituteLogo) {
			try {
				listOfStorageDto = storageHandler.getStorages(instituteId, EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.LOGO);
			} catch (Exception e) {
				log.error("Not able to fetch logo for institute id "+instituteId);
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
			instituteBasicInfoDto.setInstituteCategoryTypeName(institute.getInstituteCategoryType().getName());
		}
		instituteBasicInfoDto.setCreatedBy(institute.getCreatedBy());
		instituteBasicInfoDto.setWorldRanking(institute.getWorldRanking());
		instituteBasicInfoDto.setDomesticRanking(institute.getDomesticRanking());
		if (includeDetail) {
			instituteBasicInfoDto.setTotalCourses(courseDAO.getTotalCourseCountForInstitute(institute.getId().toString()));
			log.info("Calling review service to fetch user average review for instituteId");
			Map<String, ReviewStarDto> yuzeeReviewMap = reviewHandler.getAverageReview(EntityTypeEnum.INSTITUTE.name(),
					List.of(instituteId));

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
