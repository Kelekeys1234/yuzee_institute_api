package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.ReviewQuestions;
import com.seeka.app.dto.ReviewQuestionsDto;
import com.seeka.app.exception.ValidationException;

public interface IReviewQuestionService {

	ReviewQuestions addReviewQuestions(ReviewQuestionsDto reviewQuestionsDto) throws ValidationException;

	ReviewQuestions updateReviewQuestions(ReviewQuestionsDto reviewQuestionsDto, String questionId) throws ValidationException;

	ReviewQuestionsDto getReviewQuestion(String questionId) throws ValidationException;

	ReviewQuestions deleteReviewQuestion(String questionId) throws ValidationException;

	List<ReviewQuestions> getReviewQuestionList();

	List<ReviewQuestions> getReviewQuestionListBasedOnParam(Boolean isActive, String studentType, String studentCategory, String questionCategoryId,
			String questionId);

	ReviewQuestions getReviewQuestionOnly(String questionId) throws ValidationException;

}
