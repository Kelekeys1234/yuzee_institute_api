package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.bean.Faculty;
import com.yuzee.app.dto.FacultyDto;
import com.yuzee.app.endpoint.FacultyEndpoint;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.FacultyProcessor;

@RestController("facultyControllerV1")
public class FacultyController implements FacultyEndpoint {

	@Autowired
	private FacultyProcessor facultyProcessor;

	@Override
	public ResponseEntity<?> saveFaculty(FacultyDto faculty) throws Exception {
		facultyProcessor.saveFaculty(faculty);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Faculty Created successfully")
				.create();
	}

	@Override
	public ResponseEntity<?> getFacultyById(String facultyId) throws Exception {
		FacultyDto faculty = facultyProcessor.getFacultyById(facultyId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Faculty Fetched successfully")
				.setData(faculty).create();
	}

	@Override
	public ResponseEntity<?> getFacultyByFacultyName(String facultyName) throws Exception {
		FacultyDto faculty = facultyProcessor.getFacultyByFacultyName(facultyName);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Faculty Fetched successfully")
				.setData(faculty).create();
	}
	
	@Override
	public ResponseEntity<?> getAll() throws Exception {
		List<FacultyDto> facultyList = facultyProcessor.getAllFaculties();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Faculties Fetched successfully").setData(facultyList).create();
	}
	
	@Override
	@Deprecated
	public ResponseEntity<?> getFacultyeByCountryAndLevelId(String countryId, String levelId) throws Exception {
		List<Faculty> facultyList = facultyProcessor.getFacultyByCountryIdAndLevelId(countryId, levelId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Faculties Fetched successfully").setData(facultyList).create();
	}

	@Override
	@Deprecated
	public ResponseEntity<?> getFacultyByInstituteId(String instituteId) throws Exception {
		List<FacultyDto> faculties = facultyProcessor.getFacultyByInstituteId(instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Faculties Fetched successfully").setData(faculties).create();
	}

	@Override
	@Deprecated
	public ResponseEntity<?> getFacultyByListOfInstituteId(String instituteId) throws Exception {
		List<FacultyDto> faculties = facultyProcessor.getFacultyByListOfInstituteId(instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Faculties Fetched successfully").setData(faculties).create();
	}

	@Override
	@Deprecated
	public ResponseEntity<?> getCourseFaculty(String countryId, String levelId) throws Exception {
		List<FacultyDto> facultyList = facultyProcessor.getCourseFaculty(countryId, levelId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Faculties Fetched successfully").setData(facultyList).create();
	}
}
