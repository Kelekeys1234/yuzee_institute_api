package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.SemesterDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.endpoint.SemesterInterface;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.SemesterProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SemesterController implements SemesterInterface {

	@Autowired
	private SemesterProcessor semesterProcessor;

	@Override
	public ResponseEntity<?> getAll(final Integer pageNumber, final Integer pageSize) throws ValidationException {
		log.debug("inside SemesterController.getAll(final Integer pageNumber, final Integer pageSize) method");
		if (pageNumber < 1) {
			log.error("Page number can not be less than 1");
			throw new ValidationException("Page number can not be less than 1");
		}

		if (pageSize < 1) {
			log.error("Page size can not be less than 1");
			throw new ValidationException("Page size can not be less than 1");
		}
		return new GenericResponseHandlers.Builder().setData(semesterProcessor.getAllSemesters(pageNumber, pageSize))
				.setMessage("semesters fetched successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> saveAll(String userId, ValidList<SemesterDto> semesters) throws ValidationException {
		log.debug("inside SemesterController.saveAll method");
		return new GenericResponseHandlers.Builder().setData(semesterProcessor.saveAllSemesters(userId, semesters))
				.setMessage("semesters saved successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> update(String userId, String semesterId, SemesterDto semester)
			throws ValidationException, CommonInvokeException, ForbiddenException {
		log.debug("inside SemesterController.update method");
		return new GenericResponseHandlers.Builder()
				.setData(semesterProcessor.updateSemester(userId, semesterId, semester))
				.setMessage("semester updated successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> delete(String userId, String semesterId) throws ValidationException, ForbiddenException {
		log.debug("inside SemesterController.delete method");
		semesterProcessor.deleteSemester(userId, semesterId);
		return new GenericResponseHandlers.Builder().setMessage("semester deleted successfully")
				.setStatus(HttpStatus.OK).create();
	}
}
