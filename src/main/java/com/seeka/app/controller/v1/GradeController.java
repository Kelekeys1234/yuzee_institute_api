package com.seeka.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.GradeDto;
import com.seeka.app.endpoint.GradeInterface;
import com.seeka.app.processor.EducationSystemProcessor;

@RestController("gradeControllerV1")
public class GradeController implements GradeInterface {

	@Autowired
	private EducationSystemProcessor educationSystemProcessor;

	@Override
	public ResponseEntity<?> calculate(final GradeDto gradeDto) throws Exception {
		Double averageGPA = educationSystemProcessor.calculateGrade(gradeDto);
		return new GenericResponseHandlers.Builder().setData(averageGPA)
				.setMessage("Average Grade Calculated successfully").setStatus(HttpStatus.OK).create();
	}
}
