package com.seeka.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.EducationSystemDto;
import com.seeka.app.dto.EducationSystemRequest;
import com.seeka.app.dto.EducationSystemResponse;
import com.seeka.app.dto.GradeDto;
import com.seeka.app.endpoint.EducationSystemInterface;
import com.seeka.app.processor.EducationSystemProcessor;

@RestController("educationSystemControllerV1")
public class EducationSystemController implements EducationSystemInterface {

	@Autowired
	private EducationSystemProcessor educationSystemProcessor;

	@Override
	public ResponseEntity<?> getEducationSystems(final String countryName) throws Exception {
		List<EducationSystemDto> educationSystemList = educationSystemProcessor.getEducationSystemsByCountryName(countryName);
		return new GenericResponseHandlers.Builder().setData(educationSystemList).setMessage("Fetch Education system list successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> saveEducationSystems(final EducationSystemDto educationSystem) throws Exception {
		educationSystemProcessor.saveEducationSystems(educationSystem);
		return new GenericResponseHandlers.Builder().setMessage("Education system saved successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	@Deprecated
	public ResponseEntity<?> saveUserEducationDetails(final EducationSystemRequest educationSystemDetails) throws Exception {
		educationSystemProcessor.saveUserEducationDetails(educationSystemDetails);
		return new GenericResponseHandlers.Builder().setMessage("Create User Education system successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	@Deprecated
	public ResponseEntity<?> getEducationSystemsDetailByUserId(final String userId) throws Exception {
		EducationSystemResponse educationSystemResponse = educationSystemProcessor.getEducationSystemsDetailByUserId(userId);
		return new GenericResponseHandlers.Builder().setData(educationSystemResponse).setMessage("Get user education system details successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	@Deprecated
	public ResponseEntity<?> delete(final String userId) throws Exception {
		educationSystemProcessor.deleteEducationSystemDetailByUserId(userId);
		return new GenericResponseHandlers.Builder().setMessage("Delete user education system details successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getGrades(final String countryName, final String systemId) throws Exception {
		List<GradeDto> gradeDtos = educationSystemProcessor.getGrades(countryName, systemId);
		return new GenericResponseHandlers.Builder().setMessage("Grades fetched successfully").setData(gradeDtos)
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getEducationSystemByCountryNameAndStateName(String countryName, String stateName) throws Exception {
		List<EducationSystemDto> educationSystems = educationSystemProcessor.getEducationSystemByCountryNameAndStateName(countryName, stateName);
		return new GenericResponseHandlers.Builder().setData(educationSystems)
				.setMessage("Education System fetched Successfully").setStatus(HttpStatus.OK).create();
	}
}
