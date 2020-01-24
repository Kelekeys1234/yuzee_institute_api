package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseDeliveryMethod;
import com.seeka.app.bean.CourseIntake;
import com.seeka.app.bean.CourseLanguage;
import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.UserCompareCourse;
import com.seeka.app.bean.UserCompareCourseBundle;
import com.seeka.app.bean.YoutubeVideo;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CourseDTOElasticSearch;
import com.seeka.app.dto.CourseFilterDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.UserDto;
import com.seeka.app.exception.ValidationException;

public interface ICourseDAO {

	void save(Course obj);

	void update(Course obj);

	Course get(BigInteger id);

	List<Course> getAll();

	List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj, String searchKeyword, List<BigInteger> courseIds, Integer startIndex,
			boolean uniqueCourseName);

	List<CourseResponseDto> getAllCoursesByInstitute(BigInteger instituteId, CourseSearchDto filterObj);

	Map<String, Object> getCourse(BigInteger courseid);

	List<CourseResponseDto> getCouresesByFacultyId(BigInteger facultyId);

	List<CourseResponseDto> getCouresesByListOfFacultyId(String facultyId);

	int findTotalCount();

	List<CourseRequest> getAll(Integer pageNumber, Integer pageSize);

	List<CourseRequest> getUserCourse(BigInteger userId, Integer pageNumber, Integer pageSize, String currencyCode, String sortBy, boolean sortType)
			throws ValidationException;

	int findTotalCountByUserId(BigInteger userId);

	void saveUserCompareCourse(UserCompareCourse compareCourse);

	void saveUserCompareCourseBundle(UserCompareCourseBundle compareCourseBundle);

	List<UserCompareCourse> getUserCompareCourse(BigInteger userId);

	CourseRequest getCourseById(Integer valueOf);

	List<YoutubeVideo> getYoutubeDataforCourse(BigInteger instituteId, Set<String> keyword, Integer startIndex, Integer pageSize);

	Course getCourseData(BigInteger id);

	List<CourseResponseDto> advanceSearch(Object... values/* AdvanceSearchDto courseSearchDto */);

	List<Course> getAllCourse();

	List<CourseRequest> courseFilter(int pageNumber, Integer pageSize, CourseFilterDto courseFilter);

	int findTotalCountCourseFilter(CourseFilterDto courseFilter);

	List<CourseRequest> autoSearch(int startIndex, Integer pageSize, String searchKey);

	int autoSearchTotalCount(String searchKey);

	List<Course> facultyWiseCourseForTopInstitute(List<Faculty> facultyList, Institute institute);

	List<CourseRequest> autoSearchByCharacter(int pageNumber, Integer pageSize, String searchKey);

	List<CountryDto> getCourseCountry();

	long getCourseCountForCountry(Country country);

	List<Course> getTopRatedCoursesForCountryWorldRankingWise(Country country);

	List<Course> getAllCourseForFacultyWorldRankingWise(Long facultyId);

	List<BigInteger> getAllCourseForFacultyWorldRankingWise(BigInteger facultyId);

	List<Course> getCoursesFromId(List<BigInteger> allSearchCourses);

	Map<BigInteger, BigInteger> facultyWiseCourseIdMapForInstitute(List<Faculty> facultyList, BigInteger instituteId);

	List<Course> getAllCoursesUsingId(List<BigInteger> listOfRecommendedCourseIds);

	List<BigInteger> getTopRatedCourseIdsForCountryWorldRankingWise(Country country);

	Long getCountOfDistinctInstitutesOfferingCoursesForCountry(UserDto userDto, Country country);

	List<BigInteger> getDistinctCountryBasedOnCourses(List<BigInteger> topSearchedCourseIds);

	List<BigInteger> getCourseListForCourseBasedOnParameters(BigInteger courseId, BigInteger instituteId, BigInteger facultyId, BigInteger countryId,
			BigInteger cityId);

	List<BigInteger> getCourseIdsForCountry(final Country country);

	List<BigInteger> getAllCoursesForCountry(List<BigInteger> otherCountryIds);

	List<Long> getUserListFromUserWatchCoursesBasedOnCourses(List<BigInteger> courseIds);

	int updateCourseForCurrency(CurrencyRate currencyRate);

	int getCountforNormalCourse(CourseSearchDto courseSearchDto, String searchKeyword);

	int getCountOfAdvanceSearch(Object... values);

	Integer getTotalCourseCountForInstitute(BigInteger instituteId);

	List<CourseDTOElasticSearch> getUpdatedCourses(Date date, Integer startIndex, Integer limit);

	Integer getCountOfTotalUpdatedCourses(Date utCdatetimeAsOnlyDate);

	List<CourseDTOElasticSearch> getCoursesToBeRetriedForElasticSearch(List<BigInteger> courseIds, Integer startIndex, Integer limit);

	void saveCourseIntake(CourseIntake courseIntake);

	void deleteCourseIntake(BigInteger courseId);

	List<CourseIntake> getCourseIntakeBasedOnCourseId(BigInteger courseId);

	List<CourseIntake> getCourseIntakeBasedOnCourseIdList(List<BigInteger> courseIds);

	void saveCourseDeliveryMethod(CourseDeliveryMethod courseDeliveryMethod);

	void deleteCourseDeliveryMethod(BigInteger courseId);

	List<CourseDeliveryMethod> getCourseDeliveryMethodBasedOnCourseId(BigInteger courseId);

	List<CourseDeliveryMethod> getCourseDeliveryMethodBasedOnCourseIdList(List<BigInteger> courseIds);

	void saveCourseLanguage(CourseLanguage courseLanguage);

	void deleteCourseLanguage(BigInteger courseId);

	List<CourseLanguage> getCourseLanguageBasedOnCourseId(BigInteger courseId);

	List<String> getUserSearchCourseRecommendation(Integer startIndex, Integer pageSize, String searchKeyword);
	
	Integer getCoursesCountBylevelId(BigInteger levelId);

}
