package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.message.MessageByLocaleService;
import com.seeka.app.service.IRecommendationService;
import com.seeka.app.util.IConstant;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

	@Autowired
	private IRecommendationService iRecommendationService;

	@Autowired
	private MessageByLocaleService messageByLocalService;

	@GetMapping("/institute")
	public ResponseEntity<?> getRecommendedInstitutes(@RequestHeader(value = "userId") BigInteger userId,
			@RequestHeader(value = "language") String language, @RequestParam(value = "startIndex", required = false) Long startIndex,
			@RequestParam(value = "pageSize") Long pageSize, @RequestParam(value = "pageNumber", required=false) Long pageNumber)
			throws ValidationException, NotFoundException {

		paginationValidations(language, startIndex, pageSize, pageNumber);
		List<InstituteResponseDto> instituteResponseList = iRecommendationService.getRecommendedInstitutes(userId,
				startIndex, pageSize, pageNumber, language);

		return new GenericResponseHandlers.Builder().setData(instituteResponseList).setMessage(messageByLocalService
				.getMessage("list.display.successfully", new Object[] { IConstant.INSTITUTE }, language))
				.setStatus(HttpStatus.OK).create();
	}

	@GetMapping("/otherPeopleSearch")
	public ResponseEntity<?> getOtherPeopleSearch() {
		return null;
	}

	@GetMapping("/courses")
	public ResponseEntity<?> getRecommendedCourses(@RequestHeader(value = "userId") BigInteger userId)
			throws ValidationException {
		List<CourseResponseDto> recomendedCourses = iRecommendationService.getRecommendedCourses(userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(recomendedCourses)
				.setMessage("Recommended Course Displayed Successfully").create();
	}

	@GetMapping("/topSearchedCourses/{facultyId}")
	public ResponseEntity<?> getTopSearchedCourse(@RequestHeader(value = "userId") BigInteger userId,
			@PathVariable final BigInteger facultyId) {
		List<Course> topSearchedCourses = iRecommendationService.getTopSearchedCoursesForFaculty(facultyId, userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(topSearchedCourses)
				.setMessage("Recommended Course Displayed Successfully").create();
	}

	@GetMapping("/userCourseRelatedToSearch")
	public ResponseEntity<?> displayRelatedCourseAsPerUserPastSearch(@RequestHeader(value = "userId") BigInteger userId)
			throws ValidationException {
		Set<Course> listOfRelatedCourses = iRecommendationService.displayRelatedCourseAsPerUserPastSearch(userId);
		return new GenericResponseHandlers.Builder().setData(listOfRelatedCourses)
				.setMessage("Related Courses Display Successfully").setStatus(HttpStatus.OK).create();
	}

	private void paginationValidations(String language, Long startIndex, Long pageSize, Long pageNumber)
			throws ValidationException {
		if (pageSize == null) {
			throw new ValidationException(
					messageByLocalService.getMessage("page.size.null", new Object[] {}, language));
		}

		if (startIndex == null && pageNumber == null) {
			throw new ValidationException(messageByLocalService.getMessage("start.index.or.page.number.mandatory",
					new Object[] {}, language));
		}
	}
}
