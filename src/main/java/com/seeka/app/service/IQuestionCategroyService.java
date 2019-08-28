package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.QuestionCategroy;

public interface IQuestionCategroyService {

	QuestionCategroy addQuestionCategory(QuestionCategroy questionCategroy);

	List<QuestionCategroy> getQuestionCategoryList();

	QuestionCategroy getQuestionCategory(BigInteger questionCategoryId, Boolean isActive);
}
