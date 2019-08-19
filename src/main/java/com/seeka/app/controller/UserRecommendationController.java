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
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping(value = "/recommend/course")
	public ResponseEntity<?> createRecommendCourse(@RequestBody final UserCourseRequestDto courseRequestDto) throws ValidationException {
		userRecommendationService.createRecommendCourse(courseRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Added to recommend course.").create();
	}

	@GetMapping(value = "/recommend/course/{userId}")
	public ResponseEntity<?> getRecommendCourse(@PathVariable final BigInteger userId) throws Exception {
		List<UserWatchCourse> userMyCourseList = userRecommendationService.getRecommendCourse(userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userMyCourseList).setMessage("Get recommend course.successfully")
				.create();
	}

	@PostMapping(value = "/recommend/article")
	public ResponseEntity<?> createRecommendArticle(@RequestBody final UserArticleRequestDto userArticleRequestDto) throws ValidationException {
		userRecommendationService.createRecommendArticle(userArticleRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Added to recommend article.").create();
	}

	@GetMapping(value = "/recommend/article/{userId}")
	public ResponseEntity<?> getRecommendArticle(@PathVariable final BigInteger userId) throws Exception {
		List<UserWatchArticle> userMyCourseList = userRecommendationService.getRecommendArticle(userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userMyCourseList).setMessage("Get recommend article successfully")
				.create();
	}
}
