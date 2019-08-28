package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserReview;
import com.seeka.app.bean.UserReviewRating;
import com.seeka.app.dao.IUserReviewDao;
import com.seeka.app.dto.ReviewQuestionsDto;
import com.seeka.app.dto.UserReviewDto;
import com.seeka.app.dto.UserReviewRatingDto;
import com.seeka.app.dto.UserReviewResultDto;
import com.seeka.app.enumeration.ReviewCategory;
import com.seeka.app.enumeration.StudentCategory;
import com.seeka.app.enumeration.StudentType;
import com.seeka.app.exception.ValidationException;

/**
 *
 * @author SeekADegree
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class UserReviewService implements IUserReviewService {

	@Autowired
	private IUserReviewDao iUserReviewDao;

	@Autowired
	private IReviewQuestionService iReviewQuestionService;

	@Override
	public UserReview addUserReview(final UserReviewDto userReviewDto) throws ValidationException {
		List<UserReviewRating> ratings = userReviewDto.getRatings();
		for (UserReviewRating userReviewRating : ratings) {
			if (userReviewRating.getReviewQuestionId() == null) {
				throw new ValidationException("One of reviewQuestionId is not found");
			} else {
				if (iReviewQuestionService.getReviewQuestionOnly(userReviewRating.getReviewQuestionId()) == null) {
					throw new ValidationException("Review Question not found for id:" + userReviewRating.getReviewQuestionId());
				}
			}
			if (userReviewRating.getRating() == null) {
				throw new ValidationException("One of questions rating is not found");
			}
		}

		if (!StudentType.ALUMNI.name().equals(userReviewDto.getStudentType()) && !StudentType.CURRENT_STUDENT.name().equals(userReviewDto.getStudentType())
				&& !StudentType.BOTH.name().equals(userReviewDto.getStudentType())) {
			throw new ValidationException("Student type Should be ALUMNI, CURRENT_STUDENT Or BOTH");
		}
		if (!StudentCategory.INTERNATIONAL.name().equals(userReviewDto.getStudentCategory())
				&& !StudentCategory.DOMESTIC.name().equals(userReviewDto.getStudentCategory())) {
			throw new ValidationException("Student category Either INTERNATIONAL or DOMESTIC");
		}

		UserReview userReview = new UserReview();

		List<UserReview> userReviewList = iUserReviewDao.getUserReviewList(userReviewDto.getUserId(), userReviewDto.getEntityId(),
				userReviewDto.getEntityType());
		if (!userReviewList.isEmpty()) {
			throw new ValidationException("User review exists for same " + userReviewDto.getEntityType());
		}
		BeanUtils.copyProperties(userReviewDto, userReview);

		if (!ReviewCategory.COURSE.name().equals(userReviewDto.getEntityType()) && !ReviewCategory.INSTITUTE.name().equals(userReviewDto.getEntityType())) {
			throw new ValidationException("entity type Either COURSE or INSTITUTE");
		}

		userReview.setCreatedBy("API");
		userReview.setCreatedOn(new Date());
		iUserReviewDao.save(userReview);

		for (UserReviewRating userReviewRating : userReviewDto.getRatings()) {
			userReviewRating.setUserReview(userReview);
			iUserReviewDao.saveUserReviewRating(userReviewRating);
		}

		return userReview;
	}

	@Override
	public List<UserReviewDto> getUserReviewList(final BigInteger userId) {
		List<UserReview> userReviewList = iUserReviewDao.getUserReviewList(userId, null, null);
		List<UserReviewDto> resultList = new ArrayList<>();
		for (UserReview userReview : userReviewList) {
			UserReviewDto userReviewDto = new UserReviewDto();
			BeanUtils.copyProperties(userReview, userReviewDto);
			userReviewDto.setRatings(iUserReviewDao.getUserReviewRatings(userReview.getId()));
			resultList.add(userReviewDto);
		}
		return resultList;
	}

	@Override
	public UserReviewResultDto getUserReviewDetails(final BigInteger userReviewId) throws ValidationException {
		UserReview userReview = iUserReviewDao.getUserReview(userReviewId);
		UserReviewResultDto userReviewResultDto = new UserReviewResultDto();
		BeanUtils.copyProperties(userReview, userReviewResultDto);
		List<UserReviewRating> userReviewRatings = iUserReviewDao.getUserReviewRatings(userReview.getId());
		List<UserReviewRatingDto> resultList = new ArrayList<>();
		for (UserReviewRating userReviewRating : userReviewRatings) {
			UserReviewRatingDto userReviewRatingDto = new UserReviewRatingDto();
			ReviewQuestionsDto reviewQuestionsDto = iReviewQuestionService.getReviewQuestion(userReviewRating.getReviewQuestionId());
			BeanUtils.copyProperties(reviewQuestionsDto, userReviewRatingDto);
			userReviewRatingDto.setReviewQuestionId(userReviewRating.getReviewQuestionId());
			userReviewRatingDto.setRating(userReviewRating.getRating());
			resultList.add(userReviewRatingDto);
		}
		userReviewResultDto.setRatings(resultList);
		return userReviewResultDto;
	}

	@Override
	public List<UserReviewDto> getUserReviewBasedOnData(final BigInteger entityId, final String entityType) throws ValidationException {
		List<UserReview> userReviewList = iUserReviewDao.getUserReviewList(null, entityId, entityType);
		List<UserReviewDto> resultList = new ArrayList<>();
		for (UserReview userReview : userReviewList) {
			UserReviewDto userReviewDto = new UserReviewDto();
			BeanUtils.copyProperties(userReview, userReviewDto);
			userReviewDto.setRatings(iUserReviewDao.getUserReviewRatings(userReview.getId()));
			resultList.add(userReviewDto);
		}
		return resultList;
	}

	@Override
	public List<UserReviewRating> getUserAverageReviewBasedOnData(final BigInteger entityId, final String entityType) {
		List<Object> objectList = iUserReviewDao.getUserAverageReview(entityId, entityType);
		List<UserReviewRating> resultList = new ArrayList<>();
		for (Object object : objectList) {
			Object[] obj1 = (Object[]) object;
			UserReviewRating userReviewRating = new UserReviewRating();
			userReviewRating.setReviewQuestionId((BigInteger) obj1[0]);
			userReviewRating.setRating((Double) obj1[1]);
			resultList.add(userReviewRating);
		}
		return resultList;
	}

	@Override
	public void deleteUserReview(final BigInteger userReviewId) {
		UserReview userReview = iUserReviewDao.getUserReview(userReviewId);
		userReview.setIsActive(false);
	}

}
