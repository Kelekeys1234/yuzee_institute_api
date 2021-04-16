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
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.app.processor.InstituteFacilityProcessor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class InstituteFacilitiesController implements InstituteFacilitiesInterface {

	@Autowired
	private InstituteFacilityProcessor instituteFacilityProcessor;

	@Override
	public ResponseEntity<?> addInstituteFacilities(String instituteId,
			@RequestBody @Valid InstituteFacilityDto instituteFacilityDto) throws NotFoundException {
		log.info("Adding facilities for institute Id {}", instituteId);

		instituteFacilityProcessor.addInstituteFacility(instituteId, instituteFacilityDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Institute facilities added successfully").create();
	}

	@Override
	public ResponseEntity<?> deleteInstituteFacilitiesById(String instituteId, List<String> institutefacilitiesId) {
		log.info("deleting facilities for institute Id {}", instituteId);
		instituteFacilityProcessor.deleteInstituteFacilities(instituteId, institutefacilitiesId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Institute facilities deleted successfully").create();
	}

	@Override
	public ResponseEntity<?> getInstituteFacilities(String instituteId) {
		log.info("getting facilities for institute Id {}", instituteId);
		InstituteFacilityDto instituteFacilityDto = instituteFacilityProcessor.getFacilitiesByInstituteId(instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(instituteFacilityDto)
				.setMessage("Institute facilities fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getInstitutePublicFacilities(String instituteId) {
		log.info("getting facilities for institute Id {}", instituteId);
		InstituteFacilityDto instituteFacilityDto = instituteFacilityProcessor
				.getPublicServiceByInstituteId(instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(instituteFacilityDto)
				.setMessage("Institute public facilities fetched successfully").create();
	}
}
