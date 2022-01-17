package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.FacultyEndpoint;
import com.yuzee.app.processor.FacultyProcessor;
import com.yuzee.common.lib.dto.institute.FacultyDto;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.local.config.MessageTranslator;

@RestController("facultyControllerV1")
public class FacultyController implements FacultyEndpoint {

	@Autowired
	private FacultyProcessor facultyProcessor;
	@Autowired
	private MessageTranslator messageTranslator;
	@Override
	public ResponseEntity<?> saveFaculty(FacultyDto faculty) throws Exception {
		facultyProcessor.saveFaculty(faculty);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage(messageTranslator.toLocale("faculty.added"))
				.create();
	}

	@Override
	public ResponseEntity<?> getFacultyById(String facultyId) throws Exception {
		FacultyDto faculty = facultyProcessor.getFacultyById(facultyId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage(messageTranslator.toLocale("faculty.retrieved"))
				.setData(faculty).create();
	}

	@Override
	public ResponseEntity<?> getFacultyByFacultyName(String facultyName) throws Exception {
		FacultyDto faculty = facultyProcessor.getFacultyByFacultyName(facultyName);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage(messageTranslator.toLocale("faculty.retrieved"))
				.setData(faculty).create();
	}
	
	@Override
	public ResponseEntity<?> getAll() throws Exception {
		List<FacultyDto> facultyList = facultyProcessor.getAllFaculties();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage(messageTranslator.toLocale("faculty.retrieved")).setData(facultyList).create();
	}
}
