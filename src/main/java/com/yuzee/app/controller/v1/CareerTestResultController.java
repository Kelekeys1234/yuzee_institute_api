package com.yuzee.app.controller.v1;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.CareerTestResultInterface;
import com.yuzee.app.enumeration.CareerTestEntityType;
import com.yuzee.app.processor.CareerTestResultProcessor;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.common.lib.util.Utils;

import lombok.extern.slf4j.Slf4j;

@RestController("careerTestResultControllerV1")
@Slf4j
public class CareerTestResultController implements CareerTestResultInterface {

	@Autowired
	private CareerTestResultProcessor careerTestResultProcessor;

	@Override
	public ResponseEntity<?> saveOrUpdateResult(String userId, String entityType, List<String> entityIds) {
		log.info("inside CareerController.findByName");
		if (!EnumUtils.isValidEnum(CareerTestEntityType.class, entityType)) {
			log.error("entityType must be in one of the following {}",
					Arrays.toString(Utils.getEnumNames(CareerTestEntityType.class)));
			throw new ValidationException("entityType must be in one of the following: "
					+ Arrays.toString(Utils.getEnumNames(CareerTestEntityType.class)));
		}
		careerTestResultProcessor.saveOrUpdateResult(userId, entityType, entityIds);
		return new GenericResponseHandlers.Builder().setMessage("Careers fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> isTestCompleted(String userId) {
		return new GenericResponseHandlers.Builder().setMessage("data fetched successfully")
				.setData(careerTestResultProcessor.isTestCompleted(userId)).create();
	}
}
