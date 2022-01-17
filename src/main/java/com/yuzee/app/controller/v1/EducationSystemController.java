package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.EducationSystemInterface;
import com.yuzee.app.processor.EducationSystemProcessor;
import com.yuzee.common.lib.dto.institute.EducationSystemDto;
import com.yuzee.common.lib.dto.institute.GradeDto;
import com.yuzee.common.lib.enumeration.ResultType;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.common.lib.util.Utils;
import com.yuzee.local.config.MessageTranslator;

@RestController("educationSystemControllerV1")
public class EducationSystemController implements EducationSystemInterface {

	@Autowired
	private EducationSystemProcessor educationSystemProcessor;
	
	@Autowired
	private MessageTranslator messageTranslator;
	
	@Override
	public ResponseEntity<?> getEducationSystems(final String countryName) throws Exception {
		List<EducationSystemDto> educationSystemList = educationSystemProcessor.getEducationSystemsByCountryName(countryName);
		return new GenericResponseHandlers.Builder().setData(educationSystemList).setMessage(messageTranslator.toLocale("education_system.list.retrieved"))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> saveEducationSystems(final EducationSystemDto educationSystem) throws Exception {
		educationSystemProcessor.saveEducationSystems(educationSystem);
		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("education_system.added"))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getGrades(final String countryName, final String systemId) throws Exception {
		List<GradeDto> gradeDtos = educationSystemProcessor.getGrades(countryName, systemId);
		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("education_system.grade.list.retrieved")).setData(gradeDtos)
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getEducationSystemByCountryNameAndStateName(String countryName, String stateName) throws Exception {
		List<EducationSystemDto> educationSystems = educationSystemProcessor.getEducationSystemByCountryNameAndStateName(countryName, stateName);
		return new GenericResponseHandlers.Builder().setData(educationSystems)
				.setMessage(messageTranslator.toLocale("education_system.retrieved")).setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getResultTypes() throws Exception {
		return new GenericResponseHandlers.Builder().setData(Utils.getEnumNames(ResultType.class))
				.setMessage(messageTranslator.toLocale("education_system.result-type.retrieved")).setStatus(HttpStatus.OK).create();
	}
}
