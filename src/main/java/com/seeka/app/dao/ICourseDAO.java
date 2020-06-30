package com.seeka.app.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseDeliveryMethod;
import com.seeka.app.bean.CourseIntake;
import com.seeka.app.bean.CourseLanguage;
import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.UserCompareCourse;
import com.seeka.app.bean.UserCompareCourseBundle;
import com.seeka.app.dto.AdvanceSearchDto;
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

	Course get(String id);

	List<Course> getAll();

	List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj, String searchKeyword, List<String> courseIds, Integer startIndex,
			boolean uniqueCourseName);
	
	
	List<CourseResponseDto> getAllCoursesByInstitute(String instituteId, CourseSearchDto filterObj);

	Map<String, Object> getCourse(String courseid);

	List<CourseResponseDto> getCouresesByFacultyId(String facultyId);
	
	public List<Course> getAllCourseByInstituteIdAndFacultyIdAndStatus (String instituteId,String facultyId, boolean isActive);
	
	public List<Course> getAllCourseByInstituteIdAndFacultyId (String instituteId,String facultyId);

	List<CourseResponseDto> getCouresesByListOfFacultyId(String facultyId);

	int findTotalCount();
	
	List<CourseRequest> getAll(Integer pageNumber, Integer pageSize);

	List<CourseRequest> getUserCourse(String userId, Integer pageNumber, Integer pageSize, String currencyCode, String sortBy, boolean sortType)
			throws ValidationException;

	int findTotalCountByUserId(String userId);

	void saveUserCompareCourse(UserCompareCourse compareCourse);

	void saveUserCompareCourseBundle(UserCompareCourseBundle compareCourseBundle);

	List<UserCompareCourse> getUserCompareCourse(String userId);

	CourseRequest getCourseById(String valueOf);

	Course getCourseData(String id);

	List<CourseResponseDto> advanceSearch(Object... values/* AdvanceSearchDto courseSearchDto */);

	List<Course> getAllCourse();

	List<CourseRequest> courseFilter(int pageNumber, Integer pageSize, CourseFilterDto courseFilter);

	int findTotalCountCourseFilter(CourseFilterDto courseFilter);

	List<CourseRequest> autoSearch(int startIndex, Integer pageSize, String searchKey);

	public Long autoSearchTotalCount(String searchKey);

	List<Course> facultyWiseCourseForTopInstitute(List<Faculty> facultyList, Institute institute);

	List<CountryDto> getCourseCountry();

	long getCourseCountForCountry(String countryName);

	List<Course> getTopRatedCoursesForCountryWorldRankingWise(String countryName);

	List<Course> getAllCourseForFacultyWorldRankingWise(String facultyId);

	List<String> getAllCourseForFacultyWorldRankingWises(String facultyId);

	List<Course> getCoursesFromId(List<String> allSearchCourses);

	Map<String, String> facultyWiseCourseIdMapForInstitute(List<Faculty> facultyList, String instituteId);

	List<Course> getAllCoursesUsingId(List<String> listOfRecommendedCourseIds);

	List<String> getTopRatedCourseIdsForCountryWorldRankingWise(String countryName);

	Long getCountOfDistinctInstitutesOfferingCoursesForCountry(UserDto userDto, String countryName);

	List<String> getDistinctCountryBasedOnCourses(List<String> topSearchedCourseIds);

	List<String> getCourseListForCourseBasedOnParameters(String courseId, String instituteId, String facultyId, String countryId,
			String cityId);

	List<String> getCourseIdsForCountry(final String countryName);

	List<String> getAllCoursesForCountry(List<String> otherCountryIds);

	List<Long> getUserListFromUserWatchCoursesBasedOnCourses(List<String> courseIds);

	int updateCourseForCurrency(CurrencyRate currencyRate);

	int getCountforNormalCourse(CourseSearchDto courseSearchDto, String searchKeyword);

	int getCountOfAdvanceSearch(Object... values);

	Integer getTotalCourseCountForInstitute(String instituteId);

	List<CourseDTOElasticSearch> getUpdatedCourses(Date date, Integer startIndex, Integer limit);

	Integer getCountOfTotalUpdatedCourses(Date utCdatetimeAsOnlyDate);

	List<CourseDTOElasticSearch> getCoursesToBeRetriedForElasticSearch(List<String> courseIds, Integer startIndex, Integer limit);

	void saveCourseIntake(CourseIntake courseIntake);

	void deleteCourseIntake(String courseId);

	List<CourseIntake> getCourseIntakeBasedOnCourseId(String courseId);

	List<CourseIntake> getCourseIntakeBasedOnCourseIdList(List<String> courseIds);

	void saveCourseDeliveryMethod(CourseDeliveryMethod courseDeliveryMethod);

	void deleteCourseDeliveryMethod(String courseId);

	List<CourseDeliveryMethod> getCourseDeliveryMethodBasedOnCourseId(String courseId);

	List<CourseDeliveryMethod> getCourseDeliveryMethodBasedOnCourseIdList(List<String> courseIds);

	void saveCourseLanguage(CourseLanguage courseLanguage);

	void deleteCourseLanguage(String courseId);

	List<CourseLanguage> getCourseLanguageBasedOnCourseId(String courseId);

	List<String> getUserSearchCourseRecommendation(Integer startIndex, Integer pageSize, String searchKeyword);
	
	Integer getCoursesCountBylevelId(String levelId);

	int getDistinctCourseCountbyName(String courseName);

	List<CourseResponseDto> getDistinctCourseListByName(Integer startIndex, Integer pageSize, String courseName);
	
	public List<CourseResponseDto> getNearestCourseForAdvanceSearch(AdvanceSearchDto courseSearchDto);
	
	public List<CourseResponseDto> getCourseByCountryName(Integer startIndex, Integer pageSize, String countryName);
	
	public Integer getTotalCountOfNearestCourses(Double latitude, Double longitude, Integer initialRadius);
}
