package com.seeka.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.QuestionCategroy;
import com.seeka.app.bean.ReviewQuestions;
import com.seeka.app.dao.IReviewQuestionDao;
import com.seeka.app.dto.ReviewQuestionsDto;
import com.seeka.app.enumeration.StudentCategory;
import com.seeka.app.enumeration.StudentType;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ReviewQuestionService implements IReviewQuestionService {

	@Autowired
	private IReviewQuestionDao iReviewQuestionDao;

	@Autowired
	private IQuestionCategroyService iQuestionCategroyService;

	@Override
	public ReviewQuestions addReviewQuestions(final ReviewQuestionsDto reviewQuestionsDto) throws ValidationException {
		QuestionCategroy questionCategroy = validateReviewQuestion(reviewQuestionsDto);

		ReviewQuestions reviewQuestions = new ReviewQuestions();
		BeanUtils.copyProperties(reviewQuestionsDto, reviewQuestions);
		reviewQuestions.setCreatedBy("API");
		reviewQuestions.setCreatedOn(new Date());
		reviewQuestions.setIsActive(true);
		List<ReviewQuestions> reviewQuesionsList = iReviewQuestionDao.getReviewQuestionListBasedOnParam(true, reviewQuestionsDto.getStudentType(),
				reviewQuestionsDto.getStudentCategory(), reviewQuestionsDto.getQuestionCategoryId(), null, null);
		if (!reviewQuesionsList.isEmpty()) {
			throw new ValidationException("One Active question is alreday available for studentType:" + reviewQuestionsDto.getStudentType()
					+ ",studentCategory:" + reviewQuestionsDto.getStudentCategory() + ",questionCategory: " + questionCategroy.getCategoryName()
					+ ", To add question in same category You need to delete question for same category");
		}

		iReviewQuestionDao.addReviewQuestions(reviewQuestions);
		return reviewQuestions;
	}

	/**
	 * Validate request for question
	 *
	 * @param reviewQuestionsDto
	 * @return
	 * @throws ValidationException
	 */
	private QuestionCategroy validateReviewQuestion(final ReviewQuestionsDto reviewQuestionsDto) throws ValidationException {
		QuestionCategroy questionCategroy = iQuestionCategroyService.getQuestionCategory(reviewQuestionsDto.getQuestionCategoryId(), true);
		if (questionCategroy == null) {
			throw new ValidationException("Question category not found for id:" + reviewQuestionsDto.getQuestionCategoryId());
		}
		if (!StudentType.ALUMNI.name().equals(reviewQuestionsDto.getStudentType())
				&& !StudentType.CURRENT_STUDENT.name().equals(reviewQuestionsDto.getStudentType())
				&& !StudentType.BOTH.name().equals(reviewQuestionsDto.getStudentType())) {
			throw new ValidationException("Student type Should be ALUMNI, CURRENT_STUDENT Or BOTH");
		}
		if (!StudentCategory.INTERNATIONAL.name().equals(reviewQuestionsDto.getStudentCategory())
				&& !StudentCategory.DOMESTIC.name().equals(reviewQuestionsDto.getStudentCategory())) {
			throw new ValidationException("Student category Either INTERNATIONAL or DOMESTIC");
		}
		return questionCategroy;
	}

	@Override
	public ReviewQuestions updateReviewQuestions(final ReviewQuestionsDto reviewQuestionsDto, final String questionId) throws ValidationException {
		QuestionCategroy questionCategroy = validateReviewQuestion(reviewQuestionsDto);

		ReviewQuestions existingReviewQuestions = iReviewQuestionDao.getReviewQuestion(questionId);
		if (existingReviewQuestions == null) {
			throw new ValidationException("Review question not found for id : " + questionId);
		}

		List<ReviewQuestions> reviewQuesionsList = iReviewQuestionDao.getReviewQuestionListBasedOnParam(true, reviewQuestionsDto.getStudentType(),
				reviewQuestionsDto.getStudentCategory(), reviewQuestionsDto.getQuestionCategoryId(), null, questionId);
		if (!reviewQuesionsList.isEmpty()) {
			throw new ValidationException("One Active question is alreday available for studentType:" + reviewQuestionsDto.getStudentType()
					+ ",studentCategory:" + reviewQuestionsDto.getStudentCategory() + ",questionCategory: " + questionCategroy.getCategoryName()
					+ ", To update question in same category You need to delete question for same category");
		}

		ReviewQuestions reviewQuestions = new ReviewQuestions();
		BeanUtils.copyProperties(reviewQuestionsDto, reviewQuestions);
		reviewQuestions.setCreatedBy(existingReviewQuestions.getCreatedBy());
		reviewQuestions.setCreatedOn(existingReviewQuestions.getCreatedOn());
		reviewQuestions.setUpdatedBy("API");
		reviewQuestions.setUpdatedOn(new Date());
		reviewQuestions.setIsActive(existingReviewQuestions.getIsActive());
		reviewQuestions.setId(questionId);
		iReviewQuestionDao.updateReviewQuestions(reviewQuestions);
		return reviewQuestions;
	}

	@Override
	public ReviewQuestions getReviewQuestionOnly(final String questionId) throws ValidationException {
		return iReviewQuestionDao.getReviewQuestion(questionId);
	}

	@Override
	public ReviewQuestionsDto getReviewQuestion(final String questionId) throws ValidationException {
		ReviewQuestions reviewQuestions = iReviewQuestionDao.getReviewQuestion(questionId);
		if (reviewQuestions == null) {
			throw new ValidationException("review question not found for id : " + questionId);
		}
		ReviewQuestionsDto reviewQuestionsDto = new ReviewQuestionsDto();
		BeanUtils.copyProperties(reviewQuestions, reviewQuestionsDto);
		QuestionCategroy questionCategroy = iQuestionCategroyService.getQuestionCategory(reviewQuestionsDto.getQuestionCategoryId(), true);
		if (questionCategroy == null) {
			throw new ValidationException("Question category not found for id:" + reviewQuestionsDto.getQuestionCategoryId());
		}
		reviewQuestionsDto.setQuestionCategoryName(questionCategroy.getCategoryName());
		return reviewQuestionsDto;
	}

	@Override
	public ReviewQuestions deleteReviewQuestion(final String questionId) throws ValidationException {
		ReviewQuestions reviewQuestions = iReviewQuestionDao.getReviewQuestion(questionId);
		if (reviewQuestions == null) {
			throw new ValidationException("review question not found for id : " + questionId);
		} else if (reviewQuestions.getIsActive() != null && !reviewQuestions.getIsActive()) {
			throw new ValidationException("review question already deleted");
		}
		reviewQuestions.setUpdatedBy("API");
		reviewQuestions.setUpdatedOn(new Date());
		reviewQuestions.setDeletedOn(new Date());
		reviewQuestions.setDeletedBy("API");
		reviewQuestions.setIsActive(false);
		iReviewQuestionDao.updateReviewQuestions(reviewQuestions);
		return reviewQuestions;
	}

	@Override
	public List<ReviewQuestions> getReviewQuestionList() {
		return iReviewQuestionDao.getReviewQuestionListBasedOnParam(true, null, null, null, null, null);
	}

	@Override
	public List<ReviewQuestions> getReviewQuestionListBasedOnParam(final Boolean isActive, final String studentType, final String studentCategory,
			final String questionCategoryId, final String questionId) {
		return iReviewQuestionDao.getReviewQuestionListBasedOnParam(isActive, studentType, studentCategory, questionCategoryId, questionId, null);
	}

}
