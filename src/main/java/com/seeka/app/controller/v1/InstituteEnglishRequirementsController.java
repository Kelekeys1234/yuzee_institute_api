package com.seeka.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.InstituteEnglishRequirementsDto;
import com.seeka.app.endpoint.InstituteEnglishRequirementsInterface;
import com.seeka.app.processor.InstituteEnglishRequirementsProcessor;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteEnglishRequirementsController implements InstituteEnglishRequirementsInterface {
	
	@Autowired
	private InstituteEnglishRequirementsProcessor instituteEnglishRequirementsProcessor;
	
	@Override
	public ResponseEntity<?> addInstituteEnglishRequirements(String userId, String instituteId,
		 @RequestBody @Valid InstituteEnglishRequirementsDto instituteEnglishRequirementsDto) throws Exception {
		instituteEnglishRequirementsProcessor.addInstituteEnglishRequirements(userId, instituteId, instituteEnglishRequirementsDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Created institute english requirements successfully").create();
	}

	@Override
	public ResponseEntity<?> updateInstituteEnglishRequirements(String userId, String englishRequirementsId,
			@RequestBody @Valid InstituteEnglishRequirementsDto instituteEnglishRequirementsDto) throws Exception {
		instituteEnglishRequirementsProcessor.updateInstituteEnglishRequirements(userId, englishRequirementsId, instituteEnglishRequirementsDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Updated institute english requirements successfully").create();
	}

	@Override
	public ResponseEntity<?> getInstituteEnglishRequirementsByInstiuteId(String userId, String instituteId)
			throws Exception {
		List<InstituteEnglishRequirementsDto> listOfInstituteEnglishRequirementDto = instituteEnglishRequirementsProcessor.getListOfInstituteEnglishRequirements(userId, instituteId, "PRIVATE");
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(listOfInstituteEnglishRequirementDto).setMessage("Fetched institute english requirements successfully").create();
	}

	@Override
	public ResponseEntity<?> getInstitutePublicEnglishRequirementsByInstituteId(String instituteId) throws Exception {
		List<InstituteEnglishRequirementsDto> listOfInstituteEnglishRequirementDto = instituteEnglishRequirementsProcessor.getListOfInstituteEnglishRequirements(null, instituteId, "PUBLIC");
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(listOfInstituteEnglishRequirementDto).setMessage("Fetched public institute english requirements successfully").create();
	}

	@Override
	public ResponseEntity<?> deleteInstituteEnglishRequirementsByRequirementsId(String userId,
			String englishRequirementsId) throws Exception {
		instituteEnglishRequirementsProcessor.deleteInstituteEnglishRequirements(userId, englishRequirementsId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Deleted institute english requirements successfully").create();
	}

}
