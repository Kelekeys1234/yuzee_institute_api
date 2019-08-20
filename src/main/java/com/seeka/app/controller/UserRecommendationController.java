package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.UserWatchArticle;
import com.seeka.app.bean.UserWatchCourse;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.UserArticleRequestDto;
import com.seeka.app.dto.UserCourseRequestDto;
import com.seeka.app.exception.ValidationException;
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

	@PostMapping(value = "/watch/course")
	public ResponseEntity<?> createUserWatchCourse(@RequestBody final UserCourseRequestDto courseRequestDto) throws ValidationException {
		userRecommendationService.createUserWatchCourse(courseRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Added to watch course.").create();
	}

	@GetMapping(value = "/watch/course/{userId}")
	public ResponseEntity<?> getUserWatchCourse(@PathVariable final BigInteger userId) throws Exception {
		List<UserWatchCourse> userMyCourseList = userRecommendationService.getUserWatchCourse(userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userMyCourseList).setMessage("Get watch course.successfully").create();
	}

	@PostMapping(value = "/watch/article")
	public ResponseEntity<?> createUserWatchArticle(@RequestBody final UserArticleRequestDto userArticleRequestDto) throws ValidationException {
		userRecommendationService.createUserWatchArticle(userArticleRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Added to watch article.").create();
	}

	@GetMapping(value = "/watch/article/{userId}")
	public ResponseEntity<?> getUserWatchArticle(@PathVariable final BigInteger userId) throws Exception {
		List<UserWatchArticle> userMyCourseList = userRecommendationService.getUserWatchArticle(userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userMyCourseList).setMessage("Get watch article successfully").create();
	}

	@GetMapping(value = "/recommend/course/{courseId}")
	public ResponseEntity<?> getUserRecommendCourse(@PathVariable final BigInteger courseId, @RequestParam final BigInteger userId) throws Exception {
		List<Course> recommendCourses = userRecommendationService.getRecommendCourse(courseId, userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(recommendCourses).setMessage("Get recommend course.successfully")
				.create();
	}
}
