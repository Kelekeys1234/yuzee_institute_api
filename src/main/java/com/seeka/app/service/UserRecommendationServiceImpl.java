package com.seeka.app.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	public void createUserWatchCourse(final UserCourseRequestDto userCourseRequestDto) throws ValidationException {
		UserWatchCourse existingUserWatchCourse = userRecommendationDao.getUserWatchCourseByUserIdAndCourseId(userCourseRequestDto.getUserId(),
				userCourseRequestDto.getCourseId());
		Date now = new Date();
		if (existingUserWatchCourse != null) {
			existingUserWatchCourse.setUpdatedBy("API");
			existingUserWatchCourse.setUpdatedOn(now);
			userRecommendationDao.save(existingUserWatchCourse);
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
	public List<UserWatchCourse> getUserWatchCourse(final BigInteger userId) {
		return userRecommendationDao.getUserWatchCourse(userId);
	}

	@Override
	public void createUserWatchArticle(final UserArticleRequestDto userArticleRequestDto) throws ValidationException {
		UserWatchArticle existingUserWatchArticle = userRecommendationDao.getUserWatchArticleByUserIdAndArticleId(userArticleRequestDto.getUserId(),
				userArticleRequestDto.getArticleId());
		Date now = new Date();
		if (existingUserWatchArticle != null) {
			existingUserWatchArticle.setUpdatedBy("API");
			existingUserWatchArticle.setUpdatedOn(now);
			userRecommendationDao.save(existingUserWatchArticle);
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
	public List<UserWatchArticle> getUserWatchArticle(final BigInteger userId) {
		return userRecommendationDao.getUserWatchArticle(userId);
	}

	@Override
	public List<Course> getRecommendCourse(final BigInteger courseId, final BigInteger userId) throws ValidationException {
		Course existingCourse = iCourseService.getCourseData(courseId);
		if (existingCourse == null) {
			throw new ValidationException("Course not found for Id : " + courseId);
		}
		BigInteger facultyId = existingCourse.getFaculty().getId();
		BigInteger instituteId = existingCourse.getInstitute().getId();
		BigInteger countryId = existingCourse.getCountry().getId();
		BigInteger cityId = existingCourse.getCity().getId();
		BigDecimal price = null;
		if (existingCourse.getInternationalFee() != null) {
			price = new BigDecimal(existingCourse.getInternationalFee());
		}
		List<Course> resultList = new ArrayList<>();

		List<Course> firstCourses = userRecommendationDao.getRecommendCourse(facultyId, instituteId, countryId, cityId, price, new BigDecimal("2000"), 2, null);
		if (firstCourses.size() < 2) {
			firstCourses = userRecommendationDao.getRecommendCourse(facultyId, instituteId, countryId, cityId, price, new BigDecimal("5000"), 2, null);
		} else if (firstCourses.size() < 2) {
			firstCourses = userRecommendationDao.getRecommendCourse(facultyId, instituteId, countryId, cityId, price, new BigDecimal("10000"), 2, null);
		}
		resultList.addAll(firstCourses);
		int remainingCourse = 4 - firstCourses.size();
		List<BigInteger> courseIds = firstCourses.stream().map(i -> i.getId()).collect(Collectors.toList());
		List<Course> secondCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, cityId, price, new BigDecimal("2000"),
				remainingCourse, courseIds);
		if (secondCourses.size() < remainingCourse) {
			secondCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, cityId, price, new BigDecimal("5000"), remainingCourse,
					courseIds);
		} else if (secondCourses.size() < remainingCourse) {
			secondCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, cityId, price, new BigDecimal("10000"), remainingCourse,
					courseIds);
		}
		resultList.addAll(secondCourses);
		courseIds.addAll(secondCourses.stream().map(i -> i.getId()).collect(Collectors.toList()));
		remainingCourse = 4 - firstCourses.size() - secondCourses.size();
		List<Course> thirdCourses = new ArrayList<>();
		if (remainingCourse != 0) {
			thirdCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, null, price, new BigDecimal("2000"), remainingCourse,
					courseIds);
			if (thirdCourses.size() < remainingCourse) {
				thirdCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, null, price, new BigDecimal("5000"), remainingCourse,
						courseIds);
			} else if (thirdCourses.size() < remainingCourse) {
				thirdCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, null, price, new BigDecimal("10000"), remainingCourse,
						courseIds);
			}
			resultList.addAll(thirdCourses);
		}

		courseIds.addAll(thirdCourses.stream().map(i -> i.getId()).collect(Collectors.toList()));

		remainingCourse = 4 - firstCourses.size() - secondCourses.size() - thirdCourses.size();
		List<Course> forthCourses = new ArrayList<>();
		if (remainingCourse != 0) {
			forthCourses = userRecommendationDao.getRecommendCourse(facultyId, null, null, null, price, new BigDecimal("2000"), remainingCourse, courseIds);
			if (forthCourses.size() < remainingCourse) {
				forthCourses = userRecommendationDao.getRecommendCourse(facultyId, null, null, null, price, new BigDecimal("5000"), remainingCourse, courseIds);
			} else if (forthCourses.size() < remainingCourse) {
				forthCourses = userRecommendationDao.getRecommendCourse(facultyId, null, null, null, price, new BigDecimal("10000"), remainingCourse,
						courseIds);
			}
			resultList.addAll(forthCourses);
		}

		remainingCourse = 4 - firstCourses.size() - secondCourses.size() - thirdCourses.size() - forthCourses.size();
		if (remainingCourse < 5) {
			List<UserWatchCourse> userWatchCourses = userRecommendationDao.getUserWatchCourse(userId);
			List<Course> courseWatchList = userWatchCourses.stream().map(i -> i.getCourse()).filter(i -> !courseIds.contains(i.getId()))
					.collect(Collectors.toList());
			while (remainingCourse != 0) {
				for (Course course : courseWatchList) {
					resultList.add(course);
					remainingCourse--;
				}
			}
		}
		return resultList;
	}

}
