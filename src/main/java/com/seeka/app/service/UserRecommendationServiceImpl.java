package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.bean.UserWatchArticle;
import com.seeka.app.bean.UserWatchCourse;
import com.seeka.app.dao.UserRecommendationDao;
import com.seeka.app.dto.UserArticleRequestDto;
import com.seeka.app.dto.UserCourseRequestDto;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserRecommendationServiceImpl implements UserRecommendationService {

	@Autowired
	private UserRecommendationDao userRecommendationDao;

	@Autowired
	private ICourseService iCourseService;

	@Autowired
	private IArticleService iArticleService;

	@Override
	public void createRecommendCourse(final UserCourseRequestDto userCourseRequestDto) throws ValidationException {
		UserWatchCourse existingUserRecommendCourse = userRecommendationDao.getUserWatchCourseByUserIdAndCourseId(userCourseRequestDto.getUserId(),
				userCourseRequestDto.getCourseId());
		Date now = new Date();
		if (existingUserRecommendCourse != null) {
			existingUserRecommendCourse.setUpdatedBy("API");
			existingUserRecommendCourse.setUpdatedOn(now);
			userRecommendationDao.save(existingUserRecommendCourse);
		} else {
			Course existingCourse = iCourseService.getCourseData(userCourseRequestDto.getCourseId());
			if (existingCourse == null) {
				throw new ValidationException("Course not found for Id : " + userCourseRequestDto.getCourseId());
			}
			UserWatchCourse userWatchCourse = new UserWatchCourse();
			userWatchCourse.setCourse(existingCourse);
			userWatchCourse.setUserId(userCourseRequestDto.getUserId());
			userWatchCourse.setCreatedBy("API");
			userWatchCourse.setCreatedOn(now);
			userWatchCourse.setUpdatedBy("API");
			userWatchCourse.setUpdatedOn(now);
			userRecommendationDao.save(userWatchCourse);
		}
	}

	@Override
	public List<UserWatchCourse> getRecommendCourse(final BigInteger userId) {
		return userRecommendationDao.getRecommendCourse(userId);
	}

	@Override
	public void createRecommendArticle(final UserArticleRequestDto userArticleRequestDto) throws ValidationException {
		UserWatchArticle existingUserRecommendArticle = userRecommendationDao.getUserWatchArticleByUserIdAndArticleId(userArticleRequestDto.getUserId(),
				userArticleRequestDto.getArticleId());
		Date now = new Date();
		if (existingUserRecommendArticle != null) {
			existingUserRecommendArticle.setUpdatedBy("API");
			existingUserRecommendArticle.setUpdatedOn(now);
			userRecommendationDao.save(existingUserRecommendArticle);
		} else {
			SeekaArticles seekaArticles = iArticleService.findByArticleId(userArticleRequestDto.getArticleId());
			if (seekaArticles == null) {
				throw new ValidationException("Article not found for Id : " + userArticleRequestDto.getArticleId());
			}
			UserWatchArticle userWatchArticle = new UserWatchArticle();
			userWatchArticle.setSeekaArticles(seekaArticles);
			userWatchArticle.setUserId(userArticleRequestDto.getUserId());
			userWatchArticle.setCreatedBy("API");
			userWatchArticle.setCreatedOn(now);
			userWatchArticle.setUpdatedBy("API");
			userWatchArticle.setUpdatedOn(now);
			userRecommendationDao.save(userWatchArticle);
		}
	}

	@Override
	public List<UserWatchArticle> getRecommendArticle(final BigInteger userId) {
		return userRecommendationDao.getRecommendArticle(userId);
	}

}
