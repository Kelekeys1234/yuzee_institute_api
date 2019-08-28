package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.ReviewQuestions;
import com.seeka.app.dto.ReviewQuestionsDto;
import com.seeka.app.exception.ValidationException;

public interface IReviewQuestionService {

	ReviewQuestions addReviewQuestions(ReviewQuestionsDto reviewQuestionsDto) throws ValidationException;

	ReviewQuestions updateReviewQuestions(ReviewQuestionsDto reviewQuestionsDto, BigInteger questionId) throws ValidationException;

	ReviewQuestionsDto getReviewQuestion(BigInteger questionId) throws ValidationException;

	ReviewQuestions deleteReviewQuestion(BigInteger questionId);

	List<ReviewQuestions> getReviewQuestionList();

	List<ReviewQuestions> getReviewQuestionListBasedOnParam(Boolean isActive, String studentType, String studentCategory, BigInteger questionCategoryId,
			BigInteger questionId);

	ReviewQuestions getReviewQuestionOnly(BigInteger questionId) throws ValidationException;

}
