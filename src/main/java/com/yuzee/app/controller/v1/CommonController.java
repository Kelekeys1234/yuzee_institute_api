package com.yuzee.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.CommonEndPoint;
import com.yuzee.app.processor.CommonProcessor;
import com.yuzee.common.lib.exception.InternalServerException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.common.lib.util.ValidationUtil;
import com.yuzee.local.config.MessageTranslator;

@RestController
public class CommonController implements CommonEndPoint {

	@Autowired
	CommonProcessor commonProcessor;
	@Autowired
	private MessageTranslator messageTranslator;
	@Override
	public ResponseEntity<?> getEntityGallery(String entityType, String entityId)
			throws InternalServerException, NotFoundException, ValidationException {
		ValidationUtil.validatEntityType(entityType);
		return new GenericResponseHandlers.Builder().setData(commonProcessor.getEntityGallery(entityType, entityId))
				.setMessage(messageTranslator.toLocale("common_course.gallery.retrieved")).setStatus(HttpStatus.OK).create();
	}
}