package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.QuestionCategroy;

public interface IQuestionCategroyDao {

	void addQuestionCategory(QuestionCategroy questionCategroy);

	List<QuestionCategroy> getQuestionCategoryList();

	QuestionCategroy getQuestionCategory(BigInteger questionCategoryId, Boolean isActive);
}
