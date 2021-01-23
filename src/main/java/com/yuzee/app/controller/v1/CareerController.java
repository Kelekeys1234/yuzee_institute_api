package com.yuzee.app.controller.v1;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.CareerInterface;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CareerProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController("careerControllerV1")
@Slf4j
public class CareerController implements CareerInterface {

	@Autowired
	private CareerProcessor careerProcessor;

	@Override
	public ResponseEntity<?> findByName(@NotNull String name, Integer pageNumber, Integer pageSize) {
		log.info("inside CareerController.findByName");
		return new GenericResponseHandlers.Builder()
				.setData(careerProcessor.findCareerByName(name, pageNumber, pageSize)).setStatus(HttpStatus.OK)
				.setMessage("Careers fetched successfully").create();
	}
}
