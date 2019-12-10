package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.QuestionCategroy;
import com.seeka.app.dao.IQuestionCategroyDao;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class QuestionCategroyService implements IQuestionCategroyService {

	@Autowired
	private IQuestionCategroyDao iQuestionCategroyDao;

	@Override
	public QuestionCategroy addQuestionCategory(final QuestionCategroy questionCategroy) {
		questionCategroy.setCreatedBy("API");
		questionCategroy.setCreatedOn(new Date());
		questionCategroy.setIsActive(true);
		iQuestionCategroyDao.addQuestionCategory(questionCategroy);
		return questionCategroy;
	}

	@Override
	public List<QuestionCategroy> getQuestionCategoryList() {
		return iQuestionCategroyDao.getQuestionCategoryList();
	}

	@Override
	public QuestionCategroy getQuestionCategory(final BigInteger questionCategoryId, final Boolean isActive) {
		return iQuestionCategroyDao.getQuestionCategory(questionCategoryId, isActive);
	}

	@Override
	public void deleteQuestionCategory(final BigInteger questionCategoryId) throws ValidationException {
		QuestionCategroy questionCategroy = iQuestionCategroyDao.getQuestionCategory(questionCategoryId, true);
		if (questionCategroy == null) {
			throw new ValidationException("QuestionCategroy is not found for id" + questionCategoryId);
		}
		questionCategroy.setIsActive(false);
		iQuestionCategroyDao.update(questionCategroy);

	}

}
