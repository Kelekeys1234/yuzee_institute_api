package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.service.UserRecommendationService;

/**
 *
 * @author SeekADegree
 *
 */
@RestController
@RequestMapping("/user")
public class UserRecommendationController {

	@Autowired
	private UserRecommendationService userRecommendationService;

	@GetMapping(value = "/recommend/course/{courseId}")
	public ResponseEntity<?> getUserRecommendCourse(@PathVariable final BigInteger courseId, @RequestParam final BigInteger userId) throws Exception {
		List<Course> recommendCourses = userRecommendationService.getRecommendCourse(courseId, userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(recommendCourses).setMessage("Get recommend course successfully")
				.create();
	}

	@GetMapping(value = "/related/course/{courseId}")
	public ResponseEntity<?> getRelatedCourse(@PathVariable final BigInteger courseId) throws Exception {
		List<Course> recommendCourses = userRecommendationService.getRelatedCourse(courseId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(recommendCourses).setMessage("Get related course successfully").create();
	}
}
