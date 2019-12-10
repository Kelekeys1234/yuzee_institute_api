package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.QuestionCategroy;
import com.seeka.app.exception.ValidationException;

public interface IQuestionCategroyService {

	QuestionCategroy addQuestionCategory(QuestionCategroy questionCategroy);

	List<QuestionCategroy> getQuestionCategoryList();

	QuestionCategroy getQuestionCategory(BigInteger questionCategoryId, Boolean isActive);

	void deleteQuestionCategory(BigInteger questionCategoryId) throws ValidationException;
}
