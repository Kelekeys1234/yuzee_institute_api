package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.QuestionCategroy;

public interface IQuestionCategroyDao {

	void addQuestionCategory(QuestionCategroy questionCategroy);

	List<QuestionCategroy> getQuestionCategoryList();

	QuestionCategroy getQuestionCategory(String questionCategoryId, Boolean isActive);

	void update(QuestionCategroy questionCategroy);

	QuestionCategroy getQuestionCategoryName(String categoryName, Boolean isActive);
}
