package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Level;
import com.seeka.app.bean.UserReview;
import com.seeka.app.dao.IUserReviewDao;
import com.seeka.app.dto.UserReviewDto;
import com.seeka.app.enumeration.ReviewCategory;
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

	@Override
	public UserReview addUserReview(final UserReviewDto userReviewDto) throws ValidationException {
		UserReview userReview = new UserReview();
		if (userReviewDto.getUserId() == null) {
			throw new ValidationException("userId is required");
		} else if (userReviewDto.getEntityId() == null) {
			throw new ValidationException("EntityId is required");
		}
		List<UserReview> userReviewList = iUserReviewDao.getUserReview(userReviewDto.getUserId(), userReviewDto.getEntityId(), userReviewDto.getEntityType());
		if (!userReviewList.isEmpty()) {
			throw new ValidationException("User review exists for same " + userReviewDto.getEntityType());
		}
		BeanUtils.copyProperties(userReviewDto, userReview);
		Level level = new Level();
		level.setId(userReviewDto.getLevelId());
		userReview.setLevel(level);
		if (!ReviewCategory.COURSE.name().equals(userReviewDto.getEntityType()) && !ReviewCategory.INSTITUTE.name().equals(userReviewDto.getEntityType())) {
			throw new ValidationException("entity type Either COURSE or INSTITUTE");
		}

		userReview.setCreatedBy("API");
		userReview.setCreatedOn(new Date());
		iUserReviewDao.save(userReview);
		return userReview;
	}

	@Override
	public List<UserReview> getUserReview(final BigInteger userId) {
		return iUserReviewDao.getUserReview(userId, null, null);
	}

	@Override
	public List<UserReview> getUserReviewBasedOnData(final BigInteger entityId, final String entityType) throws ValidationException {
		return iUserReviewDao.getUserReview(null, entityId, entityType);
	}

	@Override
	public UserReview getUserAverageReviewBasedOnData(final BigInteger entityId, final String entityType) {
		return iUserReviewDao.getUserAverageReview(entityId, entityType);
	}

}
