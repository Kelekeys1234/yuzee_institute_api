package com.seeka.app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseDeliveryMethod;
import com.seeka.app.bean.CourseIntake;
import com.seeka.app.bean.CourseLanguage;
import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.YoutubeVideo;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseDTOElasticSearch;
import com.seeka.app.dto.CourseFilterDto;
import com.seeka.app.dto.CourseMinRequirementDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.UserCourse;
import com.seeka.app.dto.UserDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;

public interface ICourseService {

	Course get(String id);

	List<Course> getAll();

	List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj, Integer startIndex, Integer pageSize, String searchKeyword)
			throws ValidationException;

	List<CourseResponseDto> getAllCoursesByInstitute(String instituteId, CourseSearchDto filterObj);

	Map<String, Object> getCourse(String courseid);

	List<CourseResponseDto> getCouresesByFacultyId(String facultyId);

	List<CourseResponseDto> getCouresesByListOfFacultyId(String facultyId);

	String save(CourseRequest courseDto) throws ValidationException;

	Map<String, Object> getAllCourse(Integer pageNumber, Integer pageSize);

	Map<String, Object> deleteCourse(String courseId);

	Map<String, Object> addUserCourses(UserCourse userCourse);

	Map<String, Object> getUserCourse(String userId, Integer pageNumber, Integer pageSize, String currencyCode, String sortBy, Boolean sortAsscending)
			throws ValidationException;

	Map<String, Object> addUserCompareCourse(UserCourse userCourse);

	Map<String, Object> getUserCompareCourse(String userId);

	List<YoutubeVideo> getYoutubeDataforCourse(String courseId, Integer startIndex, final Integer pageSize);

	List<YoutubeVideo> getYoutubeDataforCourse(String instituteId, String courseName, Integer startIndex, Integer pageSize);

	Course getCourseData(String courseId);

	Map<String, Object> getAllServices();

	List<CourseResponseDto> advanceSearch(AdvanceSearchDto courseSearchDto) throws ValidationException;

	Map<String, Object> getAllCourse();

	/**
	 * Update Course based on request
	 *
	 * @param courseDto
	 * @param id
	 * @return
	 * @throws ValidationException
	 */
	String update(CourseRequest courseDto, String id) throws ValidationException;

	Map<String, Object> courseFilter(CourseFilterDto courseFilter);

	Map<String, Object> autoSearch(Integer pageNumber, Integer pageSize, String searchKey);

	List<Course> facultyWiseCourseForInstitute(List<Faculty> facultyList, Institute institute);

	void saveCourseMinrequirement(CourseMinRequirementDto obj);

	List<CourseMinRequirementDto> getCourseMinRequirement(String courseId);

	Map<String, Object> autoSearchByCharacter(String searchKey);

	long checkIfCoursesPresentForCountry(String country);

	List<Course> getTopRatedCoursesForCountryWorldRankingWise(String country);

	List<Course> getAllCourseUsingFaculty(String facultyId);

	List<String> getAllCourseUsingFacultyId(String facultyId);

	List<String> getTopSearchedCoursesByOtherUsers(String userId);

	List<Course> getCoursesById(List<String> allSearchCourses);

	Map<String, String> facultyWiseCourseIdMapForInstitute(List<Faculty> facultyList, String instituteId);

	List<Course> getAllCoursesUsingId(List<String> listOfRecommendedCourseIds);

	List<String> getTopRatedCourseIdForCountryWorldRankingWise(String country);

	List<String> getTopSearchedCoursesByUsers(String userId);

	Set<Course> getRelatedCoursesBasedOnPastSearch(List<String> courseList) throws ValidationException;

	Long getCountOfDistinctInstitutesOfferingCoursesForCountry(UserDto userDto, String country);

	List<String> getCountryForTopSearchedCourses(List<String> topSearchedCourseIds) throws ValidationException;

	List<String> courseIdsForCountry(final String country);

	List<String> courseIdsForMigratedCountries(final String country);

	List<Long> getUserListForUserWatchCourseFilter(String courseId, String instituteId, String facultyId, String countryId, String cityId);

	void updateCourseForCurrency(CurrencyRate currencyRate);

	int getCountforNormalCourse(CourseSearchDto courseSearchDto, String searchKeyword);

	int getCountOfAdvanceSearch(AdvanceSearchDto courseSearchDto) throws ValidationException, NotFoundException;
	
	int getDistinctCourseCount(String courseName);

	List<CourseDTOElasticSearch> getUpdatedCourses(Date date, Integer startIndex, Integer limit);

	Integer getCountOfTotalUpdatedCourses(Date utCdatetimeAsOnlyDate);

	List<CourseDTOElasticSearch> getCoursesToBeRetriedForElasticSearch(List<String> courseIds, Integer startIndex, Integer limit);

	List<CourseIntake> getCourseIntakeBasedOnCourseId(String courseId);

	List<CourseDeliveryMethod> getCourseDeliveryMethodBasedOnCourseId(String courseId);

	List<CourseLanguage> getCourseLanguageBasedOnCourseId(String courseId);

	List<CourseResponseDto> getCourseNoResultRecommendation(String userCountry, String facultyId, String countryId, Integer startIndex,
			Integer pageSize) throws ValidationException;
	
	List<CourseResponseDto> getDistinctCourseList(Integer startIndex, Integer pageSize, String courseName) ;

	List<String> getCourseKeywordRecommendation(String facultyId, String countryId, String levelId, Integer startIndex, Integer pageSize);

	double calculateAverageRating(Map<String, Double> googleReviewMap, Map<String, Double> seekaReviewMap, Double courseStar, String instituteId);

	Map<String, Integer> getCourseCountByLevel();
}
