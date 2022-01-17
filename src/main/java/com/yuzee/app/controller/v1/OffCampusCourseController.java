package com.yuzee.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.OffCampusCourseInterface;
import com.yuzee.app.processor.OffCampusCourseProcessor;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@RestController("offCampusCourseV1")
@Slf4j
public class OffCampusCourseController implements OffCampusCourseInterface {

	@Autowired
	private OffCampusCourseProcessor offCampusCourseProcessor;
	@Autowired
	private MessageTranslator messageTranslator;
	@Override
	public ResponseEntity<?> getOffCampusCoursesByInstituteId(String instituteId, Integer pageNumber, Integer pageSize)
			throws NotFoundException {
		log.debug("inside OffCampusCourseController.getOffCampusCoursesByInstituteId");

		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("off_campus.retrieved"))
				.setData(offCampusCourseProcessor.getOffCampusCoursesByInstituteId(instituteId, pageNumber, pageSize))
				.setStatus(HttpStatus.OK).create();
	}
}
