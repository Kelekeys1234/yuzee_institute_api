package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Course;
import com.seeka.app.dao.IGlobalStudentDataDAO;
import com.seeka.app.dao.UserRecommendationDao;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserRecommendationServiceImpl implements UserRecommendationService {

	@Autowired
	private UserRecommendationDao userRecommendationDao;

	@Autowired
	private ICourseService iCourseService;

	@Autowired
	private IStorageService iStorageService;

	@Autowired
	private IGlobalStudentDataDAO iGlobalStudentDataDAO;

	@Override
	public List<Course> getRecommendCourse(final BigInteger courseId, final BigInteger userId) throws ValidationException {
		Course existingCourse = iCourseService.getCourseData(courseId);
		if (existingCourse == null) {
			throw new ValidationException("Course not found for Id : " + courseId);
		}
		BigInteger facultyId = null;
		BigInteger instituteId = null;
		BigInteger countryId = null;
		BigInteger cityId = null;
		if (existingCourse.getFaculty() != null) {
			facultyId = existingCourse.getFaculty().getId();
		}
		if (existingCourse.getInstitute() != null) {
			instituteId = existingCourse.getInstitute().getId();
		}
		if (existingCourse.getCountry() != null) {
			countryId = existingCourse.getCountry().getId();
		}
		if (existingCourse.getCity() != null) {
			cityId = existingCourse.getCity().getId();
		}

		Double price = existingCourse.getUsdInternationFee();
		List<Course> resultList = new ArrayList<>();

		List<BigInteger> courseIds = new ArrayList<>();
		courseIds.add(courseId);
		/**
		 * same faculty , same institute , same country , same city -> with
		 * usdInternationFee +-2000, +-5000 +-10000
		 */
		List<Course> firstCourses = userRecommendationDao.getRecommendCourse(facultyId, instituteId, countryId, cityId, price, Double.valueOf(2000), 2,
				courseIds);
		if (firstCourses.size() < 2) {
			firstCourses = userRecommendationDao.getRecommendCourse(facultyId, instituteId, countryId, cityId, price, Double.valueOf(5000), 2, courseIds);
		}
		if (firstCourses.size() < 2) {
			firstCourses = userRecommendationDao.getRecommendCourse(facultyId, instituteId, countryId, cityId, price, Double.valueOf(10000), 2, courseIds);
		}
		resultList.addAll(firstCourses);

		/**
		 * same faculty , different institute , same country , same city -> with
		 * usdInternationFee +-2000, +-5000 +-10000
		 */
		int remainingCourse = 4 - resultList.size();
		courseIds = resultList.stream().map(Course::getId).collect(Collectors.toList());
		courseIds.add(courseId);
		List<Course> secondCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, cityId, price, Double.valueOf(2000), remainingCourse,
				courseIds);
		if (secondCourses.size() < remainingCourse) {
			secondCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, cityId, price, Double.valueOf(5000), remainingCourse,
					courseIds);
		}
		if (secondCourses.size() < remainingCourse) {
			secondCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, cityId, price, Double.valueOf(10000), remainingCourse,
					courseIds);
		}
		resultList.addAll(secondCourses);

		/**
		 * same faculty , different institute , same country , different city -> with
		 * usdInternationFee +-2000, +-5000 +-10000
		 */
		remainingCourse = 4 - resultList.size();
		List<Course> thirdCourses = new ArrayList<>();
		if (remainingCourse != 0) {
			courseIds = resultList.stream().map(Course::getId).collect(Collectors.toList());
			courseIds.add(courseId);
			thirdCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, null, price, Double.valueOf(2000), remainingCourse, courseIds);
			if (thirdCourses.size() < remainingCourse) {
				thirdCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, null, price, Double.valueOf(5000), remainingCourse,
						courseIds);
			}
			if (thirdCourses.size() < remainingCourse) {
				thirdCourses = userRecommendationDao.getRecommendCourse(facultyId, null, countryId, null, price, Double.valueOf(10000), remainingCourse,
						courseIds);
			}
			resultList.addAll(thirdCourses);
		}

		/**
		 * same faculty , different institute , different country , different city ->
		 * with usdInternationFee +-2000, +-5000 +-10000
		 */
		remainingCourse = 4 - resultList.size();
		List<Course> forthCourses = new ArrayList<>();
		if (remainingCourse != 0) {
			courseIds = resultList.stream().map(Course::getId).collect(Collectors.toList());
			courseIds.add(courseId);
			forthCourses = userRecommendationDao.getRecommendCourse(facultyId, null, null, null, price, Double.valueOf(2000), remainingCourse, courseIds);
			if (forthCourses.size() < remainingCourse) {
				forthCourses = userRecommendationDao.getRecommendCourse(facultyId, null, null, null, price, Double.valueOf(5000), remainingCourse, courseIds);
			} else if (forthCourses.size() < remainingCourse) {
				forthCourses = userRecommendationDao.getRecommendCourse(facultyId, null, null, null, price, Double.valueOf(10000), remainingCourse, courseIds);
			}
			resultList.addAll(forthCourses);
		}

		remainingCourse = 4 - resultList.size();

		if (remainingCourse < 5 && userId != null) {
			courseIds = resultList.stream().map(Course::getId).collect(Collectors.toList());
			courseIds.add(courseId);
			List<Course> courseWatchList = iCourseService.getAllCoursesUsingId(courseIds);

			while (remainingCourse != 0) {
				for (Course course : courseWatchList) {
					if (!courseIds.contains(course.getId())) {
						resultList.add(course);
						remainingCourse--;
					}
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
		BigInteger facultyId = null;
		BigInteger instituteId = null;
		BigInteger countryId = null;
		BigInteger cityId = null;
		if (existingCourse.getFaculty() != null) {
			facultyId = existingCourse.getFaculty().getId();
		}
		if (existingCourse.getInstitute() != null) {
			instituteId = existingCourse.getInstitute().getId();
		}
		if (existingCourse.getCountry() != null) {
			countryId = existingCourse.getCountry().getId();
		}
		if (existingCourse.getCity() != null) {
			cityId = existingCourse.getCity().getId();
		}
		String courseName = existingCourse.getName();
		Double price = existingCourse.getUsdInternationFee();
		List<Course> resultList = new ArrayList<>();

		/**
		 * Same CourseName, same faculty , same institute , same country , same city ->
		 * with usdInternationFee +-2000, +-5000 +-10000
		 */
		List<Course> firstCourses = getRelatedCoursesPrice(facultyId, instituteId, countryId, cityId, courseName, price, resultList, courseId);
		resultList.addAll(firstCourses);

		/**
		 * Same CourseName, same faculty , different institute , same country , same
		 * city -> with usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> courses = getRelatedCoursesPrice(facultyId, null, countryId, cityId, courseName, price, resultList, courseId);
			resultList.addAll(courses);
		}

		/**
		 * Same CourseName, same faculty , different institute , same country ,
		 * different city -> with usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> courses = getRelatedCoursesPrice(facultyId, null, countryId, null, courseName, price, resultList, courseId);
			resultList.addAll(courses);
		}

		/**
		 * Same CourseName, same faculty , different institute , different country ,
		 * different city -> with usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> courses = getRelatedCoursesPrice(facultyId, null, null, null, courseName, price, resultList, courseId);
			resultList.addAll(courses);
		}

		/**
		 * same faculty , different institute , same country , same city -> with
		 * usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> courses = getRelatedCoursesPrice(facultyId, null, countryId, cityId, null, price, resultList, courseId);
			resultList.addAll(courses);
		}

		/**
		 * same faculty , different institute , same country , different city -> with
		 * usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> courses = getRelatedCoursesPrice(facultyId, null, countryId, null, null, price, resultList, courseId);
			resultList.addAll(courses);
		}

		/**
		 * same faculty , different institute , different country , different city ->
		 * with usdInternationFee +-2000, +-5000 +-10000
		 */
		if (resultList.size() < 5) {
			List<Course> courses = getRelatedCoursesPrice(facultyId, null, null, null, null, price, resultList, courseId);
			resultList.addAll(courses);
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
			final String courseName, final Double price, final List<Course> resultList, final BigInteger courseId) {
		List<BigInteger> courseIds = new ArrayList<>();
		courseIds.add(courseId);
		if (!resultList.isEmpty()) {
			List<BigInteger> list = resultList.stream().map(Course::getId).collect(Collectors.toList());
			courseIds.addAll(list);
		}
		int pageSize = 5 - resultList.size();
		List<Course> courseList = userRecommendationDao.getRelatedCourse(facultyId, instituteId, countryId, cityId, price, Double.valueOf(2000), pageSize,
				courseIds, courseName);
		if (courseList.size() < 5) {
			courseList = userRecommendationDao.getRelatedCourse(facultyId, instituteId, countryId, cityId, price, Double.valueOf(5000), pageSize, courseIds,
					courseName);
		}
		if (courseList.size() < 5) {
			courseList = userRecommendationDao.getRelatedCourse(facultyId, instituteId, countryId, cityId, price, Double.valueOf(10000), pageSize, courseIds,
					courseName);
		}
		return courseList;
	}

	/**
	 * For restricted response for related course
	 */
	@Override
	public List<CourseResponseDto> getCourseRelated(final BigInteger courseId) throws ValidationException {
		List<Course> courseList = getRelatedCourse(courseId);
		return convertCourseToCourseRespone(courseList);
	}

	@Override
	public List<CourseResponseDto> getCourseRecommended(final BigInteger courseId) throws ValidationException {
		List<Course> courseList = getRecommendCourse(courseId, null);
		return convertCourseToCourseRespone(courseList);
	}

	private List<CourseResponseDto> convertCourseToCourseRespone(final List<Course> courseList) throws ValidationException {
		List<CourseResponseDto> resultList = new ArrayList<>();
		for (Course course : courseList) {
			CourseResponseDto courseResponseDto = new CourseResponseDto();
			courseResponseDto.setId(course.getId());
			courseResponseDto.setCityId(course.getCity().getId());
			courseResponseDto.setCityName(course.getCity().getName());
			courseResponseDto.setCostRange(course.getCostRange());
			courseResponseDto.setCountryId(course.getCountry().getId());
			courseResponseDto.setCountryName(course.getCountry().getName());
			courseResponseDto.setLanguage(course.getLanguage());
			courseResponseDto.setName(course.getName());
			courseResponseDto.setDuration(course.getDuration());
			courseResponseDto.setDurationTime(course.getDurationTime());
			courseResponseDto.setInstituteId(course.getInstitute().getId());
			courseResponseDto.setInstituteName(course.getInstitute().getName());
			courseResponseDto.setInternationalFee(course.getInternationalFee());
			courseResponseDto.setDomesticFee(course.getDomesticFee());
			courseResponseDto.setLocation(course.getCity().getName() + "," + course.getCountry().getName());
			courseResponseDto.setRequirements(course.getRemarks());
			courseResponseDto.setStars(course.getStars());
			courseResponseDto.setCourseRanking(course.getWorldRanking());
			courseResponseDto.setLanguageShortKey(course.getLanguage());
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(course.getInstitute().getId(), ImageCategory.INSTITUTE.toString(), null,
					"en");
			courseResponseDto.setStorageList(storageDTOList);
			resultList.add(courseResponseDto);
		}

		return resultList;
	}

	@Override
	public List<CourseResponseDto> getCourseNoResultRecommendation(final BigInteger facultyId, final BigInteger countryId, final Integer startIndex,
			final Integer pageSize) throws ValidationException {
		List<Course> courseList = userRecommendationDao.getCourseNoResultRecommendation(facultyId, countryId, null, startIndex, pageSize);
		if (!courseList.isEmpty() && courseList.size() <= pageSize) {
			List<Course> courseCountryList = userRecommendationDao.getCourseNoResultRecommendation(null, countryId,
					courseList.stream().map(Course::getId).collect(Collectors.toList()), startIndex, pageSize - courseList.size());
			courseList.addAll(courseCountryList);
		} else {
			List<Course> courseCountryList = userRecommendationDao.getCourseNoResultRecommendation(null, countryId, null, startIndex, pageSize);
			courseList.addAll(courseCountryList);
		}
		return convertCourseToCourseRespone(courseList);
	}

}
