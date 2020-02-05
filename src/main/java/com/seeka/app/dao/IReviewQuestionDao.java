package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.ReviewQuestions;

public interface IReviewQuestionDao {

	void addReviewQuestions(ReviewQuestions reviewQuestions);

	void updateReviewQuestions(ReviewQuestions reviewQuestions);

	ReviewQuestions getReviewQuestion(String questionId);

	List<ReviewQuestions> getReviewQuestionList();

	List<ReviewQuestions> getReviewQuestionListBasedOnParam(Boolean isActive, String studentType, String studentCategory, String questionCategoryId,
			String questionId, String notQuestionId);

}
