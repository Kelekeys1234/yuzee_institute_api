package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.InstituteFacilityDto;
import com.yuzee.app.endpoint.InstituteFacilitiesInterface;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.InstituteFacilityProcessor;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteFacilitiesController implements InstituteFacilitiesInterface {
	
	@Autowired
	private InstituteFacilityProcessor instituteFacilityProcessor;
	
	@Override
	public ResponseEntity<?> addInstituteFacilities(String userId, String instituteId,
			@RequestBody @Valid InstituteFacilityDto instituteFacilityDto) throws Exception {
		log.info("Adding facilities for institute Id " +instituteId);
		instituteFacilityProcessor.addInstituteFacility(userId, instituteId, instituteFacilityDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Institute facilities added successfully").create();
	}

	@Override
	public ResponseEntity<?> deleteInstituteFacilitiesById(String userId, String instituteId,
			List<String> institutefacilitiesId) throws Exception {
		log.info("deleting facilities for institute Id " +instituteId);
		instituteFacilityProcessor.deleteInstituteFacilities(userId, instituteId, institutefacilitiesId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Institute facilities deleted successfully").create();
	}

	@Override
	public ResponseEntity<?> getInstituteFacilities(String userId, String instituteId) throws Exception {
		log.info("getting facilities for institute Id " +instituteId);
		InstituteFacilityDto instituteFacilityDto = instituteFacilityProcessor.getFacilitiesByInstituteId(userId, instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(instituteFacilityDto).setMessage("Institute facilities fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getInstitutePublicFacilities(String instituteId) throws Exception {
		log.info("getting facilities for institute Id " +instituteId);
		InstituteFacilityDto instituteFacilityDto = instituteFacilityProcessor.getPublicServiceByInstituteId(instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(instituteFacilityDto).setMessage("Institute public facilities fetched successfully").create();
	}	
}
