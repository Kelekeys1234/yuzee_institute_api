package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.constant.InstituteAssociationStatus;
import com.yuzee.app.constant.InstituteAssociationType;
import com.yuzee.app.dto.InstituteAssociationDto;
import com.yuzee.app.dto.InstituteAssociationResponseDto;
import com.yuzee.app.endpoint.InstituteAssociationInterface;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.app.processor.InstituteAssociationProcessor;
import com.yuzee.common.lib.handler.GenericResponseHandlers;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteAssociationController implements InstituteAssociationInterface {

	@Autowired
	private InstituteAssociationProcessor instituteAssociationProcessor;
	
	@Override
	public ResponseEntity<?> addInstituteAssociation(String userId,
		@RequestBody @Valid InstituteAssociationDto instituteAssociationDto) throws Exception {
		boolean isAssociationValid = EnumUtils.isValidEnum(InstituteAssociationType.class, instituteAssociationDto.getAssociationType());
		if (!isAssociationValid) {
			log.error("association type value is invalid in request passed " + instituteAssociationDto.getAssociationType()
					+ " expected CAMPUS,PARTNER");
			throw new ValidationException("association type value is invalid in request passed " + instituteAssociationDto.getAssociationType()
					+ " expected CAMPUS,PARTNER");
		}
		
		if (instituteAssociationDto.getSourceInstituteId().equalsIgnoreCase(instituteAssociationDto.getDestinationInstituteId())) {
			log.error("Soure and destinatioon id should not be same");
	        throw new ValidationException("Soure and destinatioon id should not be same");
		}
		
		instituteAssociationProcessor.addInstituteAssociation(userId, instituteAssociationDto);
		return new GenericResponseHandlers.Builder().setMessage("Institute association info added successfully")
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getInstituteAssociationByAssociationTypeAndStatus(String userId, String instituteId,
			String associationType, String status) throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(InstituteAssociationStatus.class, status);
		if (!isStatusValid) {
			log.error("status value is invalid in request passed " + status
					+ " expected PENDING,REJECTED,REVOKED,ACTIVE");
			throw new ValidationException("status value is invalid in request passed " + status
					+ " expected PENDING,REJECTED,REVOKED,ACTIVE");
		}
		
		boolean isAssociationValid = EnumUtils.isValidEnum(InstituteAssociationType.class, associationType);
		if (!isAssociationValid) {
			log.error("association type value is invalid in request passed " + associationType
					+ " expected CAMPUS,PARTNER");
			throw new ValidationException("association type value is invalid in request passed " + associationType
					+ " expected CAMPUS,PARTNER");
		}
		
		List<InstituteAssociationResponseDto> listOfInstituteAssociation = instituteAssociationProcessor.getInstituteAssociationByAssociationType(userId,instituteId, associationType, status,"PRIVATE");
		return new GenericResponseHandlers.Builder().setData(listOfInstituteAssociation).setMessage("Institute association info fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getPublicInstituteAssociationByAssociationType(String instituteId, String associationType)
			throws Exception {
		boolean isAssociationValid = EnumUtils.isValidEnum(InstituteAssociationType.class, associationType);
		if (!isAssociationValid) {
			log.error("association type value is invalid in request passed " + associationType
					+ " expected CAMPUS,PARTNER");
			throw new ValidationException("association type value is invalid in request passed " + associationType
					+ " expected CAMPUS,PARTNER");
		}
		List<InstituteAssociationResponseDto> listOfInstituteAssociation = instituteAssociationProcessor.getInstituteAssociationByAssociationType(null,instituteId, associationType, "ACTIVE", "PUBLIC");
		return new GenericResponseHandlers.Builder().setData(listOfInstituteAssociation).setMessage("Institute association info fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> updateInstituteAssociation(String userId, String instituteId,
			String instituteAssociationId, String status) throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(InstituteAssociationStatus.class, status);
		if (!isStatusValid) {
			log.error("status value is invalid in request passed " + status
					+ " expected PENDING,REJECTED,REVOKED,ACTIVE");
			throw new ValidationException("status value is invalid in request passed " + status
					+ " expected PENDING,REJECTED,REVOKED,ACTIVE");
		}
		instituteAssociationProcessor.updateInstituteAssociation(instituteAssociationId, instituteId, userId, status);
		return new GenericResponseHandlers.Builder().setMessage("Institute association updated successfully")
				.setStatus(HttpStatus.OK).create();
	}
}
