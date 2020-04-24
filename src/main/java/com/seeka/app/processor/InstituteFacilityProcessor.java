package com.seeka.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.InstituteFacility;
import com.seeka.app.dao.InstituteFacilityDao;
import com.seeka.app.dto.InstituteFacilityDto;
import com.seeka.app.util.DTOUtils;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class InstituteFacilityProcessor {

	@Autowired
	private InstituteFacilityDao instituteFacilityDao;
	
	/*
	 * @Autowired private UserAccessUtils userAccessUtils;
	 */
	
	public void addInstituteFacility (String userId ,String instituteId , InstituteFacilityDto instituteFacilityDto) throws Exception {
		List<InstituteFacility> listOfFacilityToBeSaved = new ArrayList<InstituteFacility>();
		log.debug("inside addInstituteFacility() method");
		log.info("Getting all exsisting facility");
	//	userAccessUtils.validateUserAccess(userId, instituteId, "facility page", "add");
		List<InstituteFacility> listOfExsistingInstituteFacility  = instituteFacilityDao.getAllInstituteFacility(instituteId);
		if (CollectionUtils.isEmpty(listOfExsistingInstituteFacility )) {
			log.info("No institute facility present in DB saving all facility passed in request into DB");
			instituteFacilityDto.getFacilities().stream().forEach(facilityDto -> {
				InstituteFacility instituteFacility = new InstituteFacility(instituteId, facilityDto.getFacilityId(), new Date(), new Date(), "API", "API");
				log.info("Adding facility with id into DB "+facilityDto.getFacilityId());
				listOfFacilityToBeSaved.add(instituteFacility);
			});
		} else {
			log.info("checking all exsisting facility to match with facility passed in request ");
			instituteFacilityDto.getFacilities().stream().forEach(facilityDto -> {
				InstituteFacility instituteFacilityFromDB = listOfExsistingInstituteFacility.stream().filter(facilityFromDB -> facilityFromDB.getFacilityId().equalsIgnoreCase(facilityDto.getFacilityId())).findAny().orElse(null);
				if (ObjectUtils.isEmpty(instituteFacilityFromDB)) {
					log.info("No facility present for institute facility Id "+ facilityDto.getFacilityId() + " adding it to list");
					InstituteFacility instituteFacility = new InstituteFacility(instituteId, facilityDto.getFacilityId(), new Date(), new Date(), "API", "API");
					listOfFacilityToBeSaved.add(instituteFacility);
				} else {
					log.info("Institute facility present for institute facility id "+ facilityDto.getFacilityId() + " skipping it");
				}
			});	
		}
		log.info("Persisting facility list to DB ");
		instituteFacilityDao.saveInstituteFacility(listOfFacilityToBeSaved);
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void deleteInstituteFacilities (String userId, String instituteId , List<String> instituteFacilitiesId ) throws Exception { 
		log.debug("inside deleteInstituteFacilities() method");
		//userAccessUtils.validateUserAccess(userId, instituteId, "facility page", "delete");
		if (!CollectionUtils.isEmpty(instituteFacilitiesId)) {
			instituteFacilitiesId.stream().forEach(instituteFacilityId -> {
				log.info("deleting facility having institute facility Id "+instituteFacilityId+ " and institute id "+instituteId);
				instituteFacilityDao.deleteFacilityByIdAndInstituteId(instituteFacilityId, instituteId);
			});
		} else {
			log.warn("no institute facilities id passed in request");
		}
	}
	
	public InstituteFacilityDto getFacilitiesByInstituteId( String userId , String instituteId ) throws Exception {
		InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto ();
		log.debug("inside getFacilitiesByInstituteId() method");
	//	userAccessUtils.validateUserAccess(userId, instituteId, "facility page", "get");
		log.info("Getting all facilites for institute id "+instituteId);
		List<InstituteFacility> listOfExsistingInstituteFacility  = instituteFacilityDao.getAllInstituteFacility(instituteId);
		if (!CollectionUtils.isEmpty(listOfExsistingInstituteFacility)) {
			log.info("Facility from db not empty for institute id "+instituteId);
			instituteFacilityDto = DTOUtils.createInstituteFacilityResponseDto(listOfExsistingInstituteFacility);
		}
		return instituteFacilityDto;	
	}
	
	public InstituteFacilityDto getPublicServiceByInstituteId(String instituteId ) {
		InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto ();
		log.debug("inside getFacilitiesByInstituteId() method");
		log.info("Getting all facilites for institute id "+instituteId);
		List<InstituteFacility> listOfExsistingInstituteFacility  = instituteFacilityDao.getAllInstituteFacility(instituteId);
		if (!CollectionUtils.isEmpty(listOfExsistingInstituteFacility)) {
			log.info("Facility from db not empty for institute id "+instituteId);
			instituteFacilityDto = DTOUtils.createInstituteFacilityResponseDto(listOfExsistingInstituteFacility);
		}
		return instituteFacilityDto;	
	}
}
