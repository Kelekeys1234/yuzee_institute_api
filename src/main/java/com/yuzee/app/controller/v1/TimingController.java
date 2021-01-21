package com.yuzee.app.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.TimingRequestDto;
import com.yuzee.app.endpoint.TimingInterface;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.TimingProcessor;
import com.yuzee.app.util.ValidationUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TimingController implements TimingInterface {

	@Autowired
	private TimingProcessor timingProcessor;

	@Override
	public ResponseEntity<?> saveOrUpdate(String userId, @Valid TimingRequestDto timingRequestDto)
			throws ValidationException, NotFoundException {
		if (StringUtils.isEmpty(timingRequestDto.getEntityId())) {
			log.error("entity_id must not be null or empty");
		}
		ValidationUtil.validatEntityType(timingRequestDto.getEntityType());
		ValidationUtil.validatTimingType(timingRequestDto.getTimingType());
		timingProcessor.saveUpdateTiming(userId, timingRequestDto);
		return new GenericResponseHandlers.Builder().setMessage("Timing saved/updated successfuly.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByTimingId(String userId, String entityType, String entityId, String timingId)
			throws ValidationException, NotFoundException, ForbiddenException {
		ValidationUtil.validatEntityType(entityType);
		timingProcessor.deleteTiming(userId, EntityTypeEnum.valueOf(entityType), entityId, timingId);
		return new GenericResponseHandlers.Builder().setMessage("Timing deleted successfuly.").setStatus(HttpStatus.OK)
				.create();
	}
}
