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
		List<BigInteger> courseIds = firstCourses.stream().map(i -> i.getId()).collect(Collectors.toList());
		List<Course> secondCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, cityId, price, new BigDecimal("2000"),
				resultList.size(), courseIds);
		if (secondCourses.size() < resultList.size()) {
			secondCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, cityId, price, new BigDecimal("5000"), resultList.size(),
					courseIds);
		} else if (secondCourses.size() < resultList.size()) {
			secondCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, cityId, price, new BigDecimal("10000"), resultList.size(),
					courseIds);
		}
		resultList.addAll(secondCourses);
		courseIds.addAll(secondCourses.stream().map(i -> i.getId()).collect(Collectors.toList()));
		List<Course> thirdCourses = new ArrayList<>();
		if (resultList.size() != 0) {
			thirdCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, null, price, new BigDecimal("2000"), resultList.size(),
					courseIds);
			if (thirdCourses.size() < resultList.size()) {
				thirdCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, null, price, new BigDecimal("5000"), resultList.size(),
						courseIds);
			} else if (thirdCourses.size() < resultList.size()) {
				thirdCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, null, price, new BigDecimal("10000"), resultList.size(),
						courseIds);
			}
			resultList.addAll(thirdCourses);
		}

		courseIds.addAll(thirdCourses.stream().map(i -> i.getId()).collect(Collectors.toList()));

		List<Course> forthCourses = new ArrayList<>();
		if (resultList.size() != 0) {
			forthCourses = userRecommendationDao.getRecommendCourse(facultyId, null, null, null, price, new BigDecimal("2000"), resultList.size(), courseIds);
			if (forthCourses.size() < resultList.size()) {
				forthCourses = userRecommendationDao.getRecommendCourse(facultyId, null, null, null, price, new BigDecimal("5000"), resultList.size(),
						courseIds);
			} else if (forthCourses.size() < resultList.size()) {
				forthCourses = userRecommendationDao.getRecommendCourse(facultyId, null, null, null, price, new BigDecimal("10000"), resultList.size(),
						courseIds);
			}
			resultList.addAll(forthCourses);
		}

		if (resultList.size() < 5 && userId != null) {
			List<UserWatchCourse> userWatchCourses = userRecommendationDao.getUserWatchCourse(userId);
			List<Course> courseWatchList = userWatchCourses.stream().map(i -> i.getCourse()).filter(i -> !courseIds.contains(i.getId()))
					.collect(Collectors.toList());

			int remainingQty = resultList.size();
			while (remainingQty != 0) {
				for (Course course : courseWatchList) {
					resultList.add(course);
					remainingQty--;
				}
			}
		}
		return resultList;
	}

	@Override
	public List<Course> getRelatedCourse(final BigInteger courseId) throws ValidationException {
		Course existingCourse = iCourseService.getCourseData(courseId);
		if (existingCourse == null) {
			throw new ValidationException("Course not found for Id : " + courseId);
		}
		BigInteger facultyId = existingCourse.getFaculty().getId();
		BigInteger instituteId = existingCourse.getInstitute().getId();
		BigInteger countryId = existingCourse.getCountry().getId();
		BigInteger cityId = existingCourse.getCity().getId();
		String courseName = existingCourse.getName();
		BigDecimal price = null;
		if (existingCourse.getInternationalFee() != null) {
			price = new BigDecimal(existingCourse.getInternationalFee());
		}
		List<Course> resultList = new ArrayList<>();

		/**
		 * Same CourseName, same faculty , same institute , same country , same city ->
		 * with usdInternationFee +-2000, +-5000 +-10000
		 */
		List<Course> firstCourses = getRelatedCoursesPrice(facultyId, instituteId, countryId, cityId, courseName, price, resultList);
		resultList.addAll(firstCourses);

		/**
		 * Same CourseName, same faculty , different institute , same country , same
		 * city -> with usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> secondCourses = getRelatedCoursesPrice(facultyId, null, countryId, cityId, courseName, price, resultList);
			resultList.addAll(secondCourses);
		}

		/**
		 * Same CourseName, same faculty , different institute , same country ,
		 * different city -> with usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> thirdCourses = getRelatedCoursesPrice(facultyId, null, countryId, null, courseName, price, resultList);
			resultList.addAll(thirdCourses);
		}

		/**
		 * Same CourseName, same faculty , different institute , different country ,
		 * different city -> with usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> forthCourses = getRelatedCoursesPrice(facultyId, null, null, null, courseName, price, resultList);
			resultList.addAll(forthCourses);
		}

		/**
		 * same faculty , different institute , same country , same city -> with
		 * usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> forthCourses = getRelatedCoursesPrice(facultyId, null, countryId, cityId, null, price, resultList);
			resultList.addAll(forthCourses);
		}

		/**
		 * same faculty , different institute , same country , different city -> with
		 * usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> forthCourses = getRelatedCoursesPrice(facultyId, null, countryId, null, null, price, resultList);
			resultList.addAll(forthCourses);
		}

		/**
		 * same faculty , different institute , different country , different city ->
		 * with usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> forthCourses = getRelatedCoursesPrice(facultyId, null, null, null, null, price, resultList);
			resultList.addAll(forthCourses);
		}

		return resultList;
	}

	/**
	 * General method for related courses with all same parameter but different
	 * price.
	 *
	 * @param facultyId
	 * @param instituteId
	 * @param countryId
	 * @param cityId
	 * @param courseName
	 * @param price
	 * @param resultList
	 * @return
	 */
	private List<Course> getRelatedCoursesPrice(final BigInteger facultyId, final BigInteger instituteId, final BigInteger countryId, final BigInteger cityId,
			final String courseName, final BigDecimal price, final List<Course> resultList) {
		List<BigInteger> courseIds = null;
		if (!resultList.isEmpty()) {
			courseIds = resultList.stream().map(i -> i.getId()).collect(Collectors.toList());
		}
		int pageSize = 5 - resultList.size();
		List<Course> courseList = userRecommendationDao.getRelatedCourse(facultyId, instituteId, countryId, cityId, price, new BigDecimal("2000"), pageSize,
				courseIds, courseName);
		if (courseList.size() < 5) {
			courseList = userRecommendationDao.getRelatedCourse(facultyId, instituteId, countryId, cityId, price, new BigDecimal("5000"), pageSize, courseIds,
					courseName);
		} else if (courseList.size() < 5) {
			courseList = userRecommendationDao.getRelatedCourse(facultyId, instituteId, countryId, cityId, price, new BigDecimal("10000"), pageSize, courseIds,
					courseName);
		}
		return courseList;
	}

}
