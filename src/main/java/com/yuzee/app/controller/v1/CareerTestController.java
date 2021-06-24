package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CareerJobDto;
import com.yuzee.app.endpoint.CareerTestInterface;
import com.yuzee.app.processor.CareerTestProcessor;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.common.lib.util.PaginationUtil;

@RestController("careerTestControllerV1")
public class CareerTestController implements CareerTestInterface {

	@Autowired
	private CareerTestProcessor careerTestProcessor;

	@Override
	public ResponseEntity<?> getCareerJobSkills(String userId, Integer pageNumber, Integer pageSize, String levelId, String jobId) {
		PaginationResponseDto careerJobSkillDtos = careerTestProcessor.getCareerJobSkills(userId, pageNumber,
				pageSize, levelId, jobId);
		return new GenericResponseHandlers.Builder().setData(careerJobSkillDtos).setStatus(HttpStatus.OK)
				.setMessage("Career Job Skills Fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getCareerJobWorkingStyles(String userId, List<String> jobIds, Integer pageNumber, Integer pageSize) {
		PaginationResponseDto careerJobWorkingStyles = careerTestProcessor.getCareerJobWorkingStyles(userId, jobIds, pageNumber,
				pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobWorkingStyles).setStatus(HttpStatus.OK)
				.setMessage("Career Job WorkingStyles Fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getCareerJobSubjects(String userId, List<String> jobIds, Integer pageNumber, Integer pageSize) {
		PaginationResponseDto careerJobSubjects = careerTestProcessor.getCareerJobSubjects(userId, jobIds, pageNumber,
				pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobSubjects).setStatus(HttpStatus.OK)
				.setMessage("Career Job Subjects Fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getCareerJobTypes(String userId, List<String> jobIds, Integer pageNumber, Integer pageSize) {
		PaginationResponseDto careerJobTypes = careerTestProcessor.getCareerJobTypes(userId, jobIds, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobTypes).setStatus(HttpStatus.OK)
				.setMessage("Career Job Types Fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getCareerJobs(String userId, List<String> jobIds, Integer pageNumber, Integer pageSize) {
		PaginationResponseDto careerJobs = careerTestProcessor.getCareerJobs(userId, jobIds, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobs).setStatus(HttpStatus.OK)
				.setMessage("Careers Fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getRelatedCareers(List<String> careerIds, Integer pageNumber, Integer pageSize) {
		PaginationResponseDto careerJobs = careerTestProcessor.getRealtedCareers(careerIds, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobs).setStatus(HttpStatus.OK)
				.setMessage("Related Careers Fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getRelatedCourseBasedOnCareerTest(List<String> jobIds, Integer pageNumber,
			Integer pageSize) {
		PaginationResponseDto careerJobRelatedCourse = careerTestProcessor.getRelatedCourseBasedOnCareerTest(jobIds,
				pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobRelatedCourse).setStatus(HttpStatus.OK)
				.setMessage("Related Courses fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getCareerJobById(String jobId) throws NotFoundException {
		CareerJobDto careerJobDto = careerTestProcessor.getCareerJobById(jobId);
		return new GenericResponseHandlers.Builder().setData(careerJobDto).setStatus(HttpStatus.OK)
				.setMessage("Career Job fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getCareerJobsByName(String userId, String name, Integer pageNumber, Integer pageSize) throws ValidationException {
		PaginationUtil.validatePageNoAndPageSize(pageNumber, pageSize);
		PaginationResponseDto careerJobs = careerTestProcessor.getCareerJobsByName(userId, name, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(careerJobs).setStatus(HttpStatus.OK)
				.setMessage("Career Jobs Fetched successfully").create();
	}
}
