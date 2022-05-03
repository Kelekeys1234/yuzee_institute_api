package com.yuzee.app.controller.v1;

import java.util.List;
import java.util.Locale;

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
import com.yuzee.app.processor.InstituteAssociationProcessor;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.common.lib.util.Utils;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteAssociationController implements InstituteAssociationInterface {

	@Autowired
	private InstituteAssociationProcessor instituteAssociationProcessor;

	@Autowired
	private MessageTranslator messageTranslator;

	@Override
	public ResponseEntity<?> addInstituteAssociation(String userId,
		@RequestBody @Valid InstituteAssociationDto instituteAssociationDto) throws Exception {
		boolean isAssociationValid = EnumUtils.isValidEnum(InstituteAssociationType.class, instituteAssociationDto.getAssociationType());
		if (!isAssociationValid) {
			log.error(messageTranslator.toLocale("institute-association.type.invalid",Utils.getEnumNamesAsString(InstituteAssociationType.class),Locale.US));
			throw new ValidationException(messageTranslator.toLocale("institute-association.type.invalid",Utils.getEnumNamesAsString(InstituteAssociationType.class)));
		}
		
		if (instituteAssociationDto.getSourceInstituteId().equalsIgnoreCase(instituteAssociationDto.getDestinationInstituteId())) {
			log.error(messageTranslator.toLocale("institute-association.same_source_destination_institute_id",Locale.US));
	        throw new ValidationException(messageTranslator.toLocale("institute-association.source_id"));
		}
		
		instituteAssociationProcessor.addInstituteAssociation(userId, instituteAssociationDto);
		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("institute_association.added"))
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getInstituteAssociationByAssociationTypeAndStatus(String userId, String instituteId,
			String associationType, String status) throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(InstituteAssociationStatus.class, status);
		if (!isStatusValid) {
			log.error(messageTranslator.toLocale("institute-association.status.invalid",Utils.getEnumNamesAsString(InstituteAssociationStatus.class),Locale.US));
			throw new ValidationException(messageTranslator.toLocale("institute-association.status.invalid",Utils.getEnumNamesAsString(InstituteAssociationStatus.class)));
		}
		
		boolean isAssociationValid = EnumUtils.isValidEnum(InstituteAssociationType.class, associationType);
		if (!isAssociationValid) {
			log.error(messageTranslator.toLocale("institute-association.type.invalid",Utils.getEnumNamesAsString(InstituteAssociationType.class),Locale.US));
			throw new ValidationException(messageTranslator.toLocale("institute-association.type.invalid",Utils.getEnumNamesAsString(InstituteAssociationType.class)));
		}
		
		List<InstituteAssociationResponseDto> listOfInstituteAssociation = instituteAssociationProcessor.getInstituteAssociationByAssociationType(userId,instituteId, associationType, status,"PRIVATE");
		return new GenericResponseHandlers.Builder().setData(listOfInstituteAssociation).setMessage(messageTranslator.toLocale("institute_association.retrieved"))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getPublicInstituteAssociationByAssociationType(String instituteId, String associationType)
			throws Exception {
		boolean isAssociationValid = EnumUtils.isValidEnum(InstituteAssociationType.class, associationType);
		if (!isAssociationValid) {
			log.error(messageTranslator.toLocale("institute-association.type.invalid",Utils.getEnumNamesAsString(InstituteAssociationType.class),Locale.US));
			throw new ValidationException(messageTranslator.toLocale("institute-association.type.invalid",Utils.getEnumNamesAsString(InstituteAssociationType.class)));
		}
		List<InstituteAssociationResponseDto> listOfInstituteAssociation = instituteAssociationProcessor.getInstituteAssociationByAssociationType(null,instituteId, associationType, "ACTIVE", "PUBLIC");
		return new GenericResponseHandlers.Builder().setData(listOfInstituteAssociation).setMessage(messageTranslator.toLocale("institute_association.retrieved"))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> updateInstituteAssociation(String userId, String instituteId,
			String instituteAssociationId, String status) throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(InstituteAssociationStatus.class, status);
		if (!isStatusValid) {
			log.error(messageTranslator.toLocale("institute-association.status.invalid",Utils.getEnumNamesAsString(InstituteAssociationStatus.class),Locale.US));
			throw new ValidationException(messageTranslator.toLocale("institute-association.status.invalid",Utils.getEnumNamesAsString(InstituteAssociationStatus.class)));
		}
		instituteAssociationProcessor.updateInstituteAssociation(instituteAssociationId, instituteId, userId, status);
		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("institute_association.updated"))
				.setStatus(HttpStatus.OK).create();
	}
}
