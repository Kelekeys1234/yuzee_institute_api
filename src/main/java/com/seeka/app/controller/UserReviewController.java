package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.UserReview;
import com.seeka.app.bean.UserReviewRating;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.UserReviewDto;
import com.seeka.app.dto.UserReviewResultDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IUserReviewService;

/**
 *
 * @author SeekADegree
 *
 */
@RestController
@RequestMapping("/review")
public class UserReviewController {

	@Autowired
	private IUserReviewService iUserReview;

	@PostMapping("/user")
	public ResponseEntity<?> addUserReview(@Valid @RequestBody final UserReviewDto userReviewDto) throws ValidationException {
		UserReview userReview = iUserReview.addUserReview(userReviewDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userReview).setMessage("Created user review successfully").create();
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserReview(@PathVariable final BigInteger userId) {
		List<UserReviewDto> userReviewList = iUserReview.getUserReviewList(userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userReviewList).setMessage("Get user review successfully").create();
	}

	@GetMapping
	public ResponseEntity<?> getUserReviewBasedOnData(@RequestParam(name = "entityId") final BigInteger entityId,
			@RequestParam(name = "entityType") final String entityType) throws ValidationException {
		List<UserReviewDto> userReviewList = iUserReview.getUserReviewBasedOnData(entityId, entityType);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userReviewList).setMessage("Get user review successfully").create();
	}

	@GetMapping("/average")
	public ResponseEntity<?> getUserAverageReviewBasedOnData(@RequestParam(name = "entityId") final BigInteger entityId,
			@RequestParam(name = "entityType") final String entityType) throws ValidationException {
		List<UserReviewRating> userReview = iUserReview.getUserAverageReviewBasedOnData(entityId, entityType);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userReview).setMessage("Get user average review successfully").create();
	}

	@DeleteMapping("/{userReviewId}")
	public ResponseEntity<?> deleteUserReview(@PathVariable final BigInteger userReviewId) throws ValidationException {
		iUserReview.deleteUserReview(userReviewId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Deleted user review successfully").create();
	}

	@GetMapping("/user/{userReviewId}")
	public ResponseEntity<?> getUserReviewDetails(@PathVariable final BigInteger userReviewId) throws ValidationException {
		UserReviewResultDto userReview = iUserReview.getUserReviewDetails(userReviewId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userReview).setMessage("Get user review details successfully").create();
	}
}
