package com.yuzee.app.processor;

import java.util.*;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteFacility;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.FacilityDto;
import com.yuzee.app.dto.InstituteFacilityDto;
import com.yuzee.app.util.CommonUtil;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstituteFacilityProcessor {

	@Autowired
	private InstituteDao instituteDao;


	@Autowired
	private MessageTranslator messageTranslator;

	public void addInstituteFacility(String instituteId, InstituteFacilityDto instituteFacilityDto)
			throws NotFoundException {
		List<InstituteFacility> listOfFacilityToBeSaved = new ArrayList<>();
		log.debug("inside addInstituteFacility() method");
			//userAccessUtils.validateUserAccess(userId, instituteId, "facility page", "add");

		Optional<Institute> institute = instituteDao.getInstituteByInstituteId(UUID.fromString(instituteId));
		if (institute.isEmpty()) {
			log.error(messageTranslator.toLocale("institute.id.illegal",instituteId,Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("institute.id.illegal",instituteId));
		}

		log.info("checking all existing facility to match with facility passed in request ");
		List<InstituteFacility> listOfExistingInstituteFacility = institute.get().getInstituteFacilities();

		for (FacilityDto facilityDto : instituteFacilityDto.getFacilities()) {
			InstituteFacility instituteFacilityFromDB = listOfExistingInstituteFacility.stream().filter(
					facilityFromDB -> facilityFromDB.getService().getId().toString().equalsIgnoreCase(facilityDto.getFacilityId()))
					.findAny().orElse(null);

			if (ObjectUtils.isEmpty(instituteFacilityFromDB)) {
				log.info("No facility present for institute facility Id {} adding it to list",
						facilityDto.getFacilityId());

				com.yuzee.app.bean.Service service = instituteDao.getServiceById(facilityDto.getFacilityId());
				if (ObjectUtils.isEmpty(service)) {
					log.error(messageTranslator.toLocale("institute.facility.id.illegal",facilityDto.getFacilityId(),Locale.US));
					throw new NotFoundException(messageTranslator.toLocale("institute.facility.id.illegal",facilityDto.getFacilityId()));
				}
				InstituteFacility instituteFacility = new InstituteFacility(institute.get().getId(), service);

				listOfFacilityToBeSaved.add(instituteFacility);
			} else {
				log.info("Institute facility present for institute facility id {} skipping it",
						facilityDto.getFacilityId());
			}
		}

		log.info("Persisting facility list to DB ");
		institute.get().setInstituteFacilities(listOfFacilityToBeSaved);
		instituteDao.addUpdateInstitute(institute.get());
	}

	@Transactional(rollbackOn = Throwable.class)
	public void deleteInstituteFacilities(String instituteId, List<String> instituteFacilitiesId) {
		log.debug("inside deleteInstituteFacilities() method");
		//userAccessUtils.validateUserAccess(userId, instituteId, "facility page", "delete");

		if (!CollectionUtils.isEmpty(instituteFacilitiesId)) {
			instituteFacilitiesId.forEach(instituteFacilityId -> {
				log.info("deleting facility having institute facility Id {} and institute id {}", instituteFacilityId,
						instituteId);
				instituteDao.deleteFacilityByIdAndInstituteId(instituteFacilityId, instituteId);
			});
		} else {
			log.warn("no institute facilities id passed in request");
		}
	}

	public InstituteFacilityDto getFacilitiesByInstituteId(String instituteId) {
		InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto();
		log.debug("inside getFacilitiesByInstituteId() method");
		//	userAccessUtils.validateUserAccess(userId, instituteId, "facility page", "get");
		log.info("Getting all facilities for institute id " + instituteId);
		List<InstituteFacility> listOfExistingInstituteFacility = instituteDao
				.getAllInstituteFacility(instituteId);
		if (!CollectionUtils.isEmpty(listOfExistingInstituteFacility)) {
			log.info("Facility from db not empty for institute id {}", instituteId);
			instituteFacilityDto = CommonUtil.createInstituteFacilityResponseDto(listOfExistingInstituteFacility);
		}
		return instituteFacilityDto;
	}

	public InstituteFacilityDto getPublicServiceByInstituteId(String instituteId) {
		InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto();
		log.debug("inside getFacilitiesByInstituteId() method");
		log.info("Getting all facilities for institute id {}", instituteId);
		List<InstituteFacility> listOfExistingInstituteFacility = instituteDao
				.getAllInstituteFacility(instituteId);
		if (!CollectionUtils.isEmpty(listOfExistingInstituteFacility)) {
			log.info("Facility from db not empty for institute id {}", instituteId);
			//instituteFacilityDto = DTOUtils.createInstituteFacilityResponseDto(listOfExistingInstituteFacility);
			CommonUtil.createInstituteFacilityResponseDto(listOfExistingInstituteFacility);
		}
		return instituteFacilityDto;
	}
}
