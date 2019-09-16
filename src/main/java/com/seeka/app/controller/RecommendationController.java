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
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IRecommendationService;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

	@Autowired
	private IRecommendationService iRecommendationService;
	
	@GetMapping("/institute")
	public ResponseEntity<?> getRecommendedInstitutes(){
		return null;
	}
	
	@GetMapping("/otherPeopleSearch")
	public ResponseEntity<?> getOtherPeopleSearch(){
		return null;
	}
	
	@GetMapping("/courses")
	public ResponseEntity<?> getRecommendedCourses(@RequestHeader(value="userId")BigInteger userId) throws ValidationException{
		List<Course> recomendedCourses =  iRecommendationService.getRecommendedCourses(userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(recomendedCourses).setMessage("Recommended Course Displayed Successfully")
				.create();
	}
	
	@GetMapping("/topSearchedCourses/{facultyId}")
	public ResponseEntity<?> getTopSearchedCourse(@RequestHeader(value="userId")BigInteger userId,
			@PathVariable final BigInteger facultyId){
		List<Course> topSearchedCourses = iRecommendationService.getTopSearchedCoursesForFaculty(facultyId, userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(topSearchedCourses).setMessage("Recommended Course Displayed Successfully")
				.create();
	}
	
	@GetMapping("/userCourseRelatedToSearch")
	public ResponseEntity<?> displayRelatedCourseAsPerUserPastSearch(@RequestHeader(value="userId")BigInteger userId) throws ValidationException{
		Set<Course> listOfRelatedCourses = iRecommendationService.displayRelatedCourseAsPerUserPastSearch(userId);
		return new GenericResponseHandlers.Builder().setData(listOfRelatedCourses).setMessage("Related Courses Display Successfully").setStatus(HttpStatus.OK).create();
	}
}
