package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.endpoint.CareerTestInterface;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CareerTestProcessor;

@RestController("careerTestControllerV1")
public class CareerTestController implements CareerTestInterface {

	@Autowired
	private CareerTestProcessor careerTestProcessor;

	@Override
	public ResponseEntity<?> getCareerJobSkills(String levelId, Integer pageNumber, Integer pageSize) {
		PaginationResponseDto careerJobSkillDtos = careerTestProcessor.getCareerJobSkills(levelId, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobSkillDtos).setStatus(HttpStatus.OK)
				.setMessage("Career Job Skills Fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getCareerJobWorkingStyles(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		PaginationResponseDto careerJobWorkingStyles = careerTestProcessor.getCareerJobWorkingStyles(jobIds, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobWorkingStyles).setStatus(HttpStatus.OK)
				.setMessage("Career Job WorkingStyles Fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getCareerJobSubjects(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		PaginationResponseDto careerJobSubjects = careerTestProcessor.getCareerJobSubjects(jobIds, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobSubjects).setStatus(HttpStatus.OK)
				.setMessage("Career Job Subjects Fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getCareerJobTypes(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		PaginationResponseDto careerJobTypes = careerTestProcessor.getCareerJobTypes(jobIds, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobTypes).setStatus(HttpStatus.OK)
				.setMessage("Career Job Types Fetched successfully").create();
	}
	
	@Override
	public ResponseEntity<?> getCareerJobs(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		PaginationResponseDto careerJobs = careerTestProcessor.getCareerJobs(jobIds, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobs).setStatus(HttpStatus.OK)
				.setMessage("Career Jobs Fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getRelatedCourseBasedOnCareerTest(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		PaginationResponseDto careerJobRelatedCourse = careerTestProcessor.getRelatedCourseBasedOnCareerTest(jobIds, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobRelatedCourse)
				.setStatus(HttpStatus.OK).setMessage("Career Jobs Fetched successfully").create();
	}
}
