package com.yuzee.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.CommonEndPoint;
import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CommonProcessor;
import com.yuzee.app.util.ValidationUtil;

@RestController
public class CommonController implements CommonEndPoint {

	@Autowired
	CommonProcessor commonProcessor;

	@Override
	public ResponseEntity<?> getEntityGallery(String entityType, String entityId)
			throws InternalServerException, NotFoundException, ValidationException {
		ValidationUtil.validatEntityType(entityType);
		return new GenericResponseHandlers.Builder().setData(commonProcessor.getEntityGallery(entityType, entityId))
				.setMessage("Course gallery media fetched successfully").setStatus(HttpStatus.OK).create();
	}
}