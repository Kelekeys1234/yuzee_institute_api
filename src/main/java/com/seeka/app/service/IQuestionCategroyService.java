package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.QuestionCategroy;
import com.seeka.app.exception.ValidationException;

public interface IQuestionCategroyService {

	QuestionCategroy addQuestionCategory(QuestionCategroy questionCategroy) throws ValidationException;

	List<QuestionCategroy> getQuestionCategoryList();

	QuestionCategroy getQuestionCategory(String questionCategoryId, Boolean isActive);

	void deleteQuestionCategory(String questionCategoryId) throws ValidationException;
}
