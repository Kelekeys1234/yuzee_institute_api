
package com.seeka.app.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.ReviewQuestions;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.ReviewQuestionsDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IReviewQuestionService;

/**
 *
 * @author SeekADegree
 *
 */
@RestController("reviewQuestionControllerV1")
@RequestMapping("/v1/review/question")
public class ReviewQuestionController {

	@Autowired
	private IReviewQuestionService iReviewQuestionService;

	@PostMapping
	public ResponseEntity<?> addReviewQuestions(@Valid @RequestBody final ReviewQuestionsDto reviewQuestionsDto, final BindingResult bindingResult)
			throws ValidationException {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		ReviewQuestions reviewQuestions = iReviewQuestionService.addReviewQuestions(reviewQuestionsDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(reviewQuestions).setMessage("Created review question successfully")
				.create();
	}

	@PutMapping("/{questionId}")
	public ResponseEntity<?> updateReviewQuestions(@PathVariable final String questionId, @Valid @RequestBody final ReviewQuestionsDto reviewQuestionsDto,
			final BindingResult bindingResult) throws ValidationException {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		ReviewQuestions reviewQuestions = iReviewQuestionService.updateReviewQuestions(reviewQuestionsDto, questionId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(reviewQuestions).setMessage("Updated review question successfully")
				.create();
	}

	@GetMapping("/{questionId}")
	public ResponseEntity<?> getReviewQuestion(@PathVariable final String questionId) throws ValidationException {
		ReviewQuestionsDto reviewQuestions = iReviewQuestionService.getReviewQuestion(questionId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(reviewQuestions).setMessage("Get review question successfully").create();
	}

	@DeleteMapping("/{questionId}")
	public ResponseEntity<?> deleteReviewQuestion(@PathVariable final String questionId) throws ValidationException {
		ReviewQuestions reviewQuestions = iReviewQuestionService.deleteReviewQuestion(questionId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(reviewQuestions).setMessage("Deleted review question successfully")
				.create();
	}

	@GetMapping
	public ResponseEntity<?> getReviewQuestionList() throws ValidationException {
		List<ReviewQuestions> reviewQuestionsList = iReviewQuestionService.getReviewQuestionList();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(reviewQuestionsList).setMessage("Get review question list successfully")
				.create();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getReviewQuestionList(@RequestParam(name = "isActive", required = false) final Boolean isActive,
			@RequestParam(name = "studentType", required = false) final String studentType,
			@RequestParam(name = "studentCategory", required = false) final String studentCategory,
			@RequestParam(name = "questionCategoryId", required = false) final String questionCategoryId) throws ValidationException {
		List<ReviewQuestions> reviewQuestionsList = iReviewQuestionService.getReviewQuestionListBasedOnParam(isActive, studentType, studentCategory,
				questionCategoryId, null);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(reviewQuestionsList).setMessage("Get review question list successfully")
				.create();
	}
}
