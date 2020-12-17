package com.yuzee.app.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.OffCampusCourseRequestDto;
import com.yuzee.app.endpoint.OffCampusCourseInterface;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.OffCampusCourseProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController("offCampusCourseV1")
@Slf4j
public class OffCampusCourseController implements OffCampusCourseInterface {

	@Autowired
	private OffCampusCourseProcessor offCampusCourseProcessor;

	@Override
	public ResponseEntity<?> save(String userId, @Valid OffCampusCourseRequestDto offCampusCourseRequestDto)
			throws ValidationException, CommonInvokeException {
		log.debug("inside OffCampusCourseController.save");

		offCampusCourseProcessor.saveOffCampusCourse(userId, offCampusCourseRequestDto);
		return new GenericResponseHandlers.Builder().setMessage("Off campus created successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> update(String userId, String offCampusCourseId,
			@Valid OffCampusCourseRequestDto offCampusCourseRequestDto)
			throws ValidationException, CommonInvokeException {
		log.debug("inside OffCampusCourseController.update");

		offCampusCourseProcessor.updateOffCampusCourse(userId, offCampusCourseId, offCampusCourseRequestDto);
		return new GenericResponseHandlers.Builder().setMessage("Off campus created successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> delete(String userId, String offCampusCourseId) {
		log.debug("inside OffCampusCourseController.delete");

		offCampusCourseProcessor.deleteOffCampusCourse(userId, offCampusCourseId);
		return new GenericResponseHandlers.Builder().setMessage("Off campus created successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getOffCampusCoursesByInstituteId(String instituteId, Integer pageNumber, Integer pageSize)
			throws NotFoundException {
		log.debug("inside OffCampusCourseController.getOffCampusCoursesByInstituteId");

		return new GenericResponseHandlers.Builder().setMessage("Off campus fetched successfully")
				.setData(offCampusCourseProcessor.getOffCampusCoursesByInstituteId(instituteId, pageNumber, pageSize))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getOffCampusCourseById(String offCampusCourseId)
			throws NotFoundException, ValidationException {
		log.debug("inside OffCampusCourseController.getOffCampusCoursesByInstituteId");

		return new GenericResponseHandlers.Builder().setMessage("Off campus fetched successfully")
				.setData(offCampusCourseProcessor.getOffCampusCourseResponseDtoById(offCampusCourseId))
				.setStatus(HttpStatus.OK).create();
	}
}
