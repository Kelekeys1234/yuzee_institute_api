package com.yuzee.app.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseIntake;
import com.yuzee.app.bean.CourseLanguage;
import com.yuzee.app.bean.Faculty;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseDTOElasticSearch;
import com.yuzee.app.dto.CourseDto;
import com.yuzee.app.dto.CourseFilterDto;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.CourseResponseDto;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.CurrencyRateDto;
import com.yuzee.app.dto.UserDto;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.ValidationException;

public interface CourseDao {

	public void addUpdateCourse(Course obj);

	public Course get(String id);

	public List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj, String searchKeyword, List<String> courseIds, Integer startIndex,
			boolean uniqueCourseName, List<String> entityIds);
	
	public List<CourseResponseDto> getAllCoursesByInstitute(String instituteId, CourseSearchDto filterObj);

	public Map<String, Object> getCourse(String courseid);

	public List<CourseResponseDto> getCouresesByFacultyId(String facultyId);
	
	public List<Course> getAllCourseByInstituteIdAndFacultyIdAndStatus (String instituteId,String facultyId, boolean isActive);
	
	public List<Course> getAllCourseByInstituteIdAndFacultyId (String instituteId,String facultyId);

	public List<CourseResponseDto> getCouresesByListOfFacultyId(String facultyId);

	public int findTotalCount();
	
	public List<CourseRequest> getAll(Integer pageNumber, Integer pageSize);

	public List<CourseDto> getUserCourse(List<String> courseIds, String sortBy, boolean sortType) throws ValidationException, 
			CommonInvokeException;

	public int findTotalCountByUserId(String userId);

	public Course getCourseData(String id);

	public List<CourseResponseDto> advanceSearch(List<String> entityIds,Object... values) throws CommonInvokeException;

	public List<CourseRequest> courseFilter(int pageNumber, Integer pageSize, CourseFilterDto courseFilter);

	public int findTotalCountCourseFilter(CourseFilterDto courseFilter);

	public List<CourseRequest> autoSearch(int startIndex, Integer pageSize, String searchKey);

	public Long autoSearchTotalCount(String searchKey);

	public List<Course> facultyWiseCourseForTopInstitute(List<Faculty> facultyList, Institute institute);

	public long getCourseCountForCountry(String countryName);

//	public List<Course> getTopRatedCoursesForCountryWorldRankingWise(String countryName);

//	public List<Course> getAllCourseForFacultyWorldRankingWise(String facultyId);

//	public List<String> getAllCourseForFacultyWorldRankingWises(String facultyId);

//	public List<Course> getCoursesFromId(List<String> allSearchCourses);

//	public Map<String, String> facultyWiseCourseIdMapForInstitute(List<Faculty> facultyList, String instituteId);

	public List<Course> getAllCoursesUsingId(List<String> listOfRecommendedCourseIds);

//	public List<String> getTopRatedCourseIdsForCountryWorldRankingWise(String countryName);

	public Long getCountOfDistinctInstitutesOfferingCoursesForCountry(UserDto userDto, String countryName);

	public List<String> getDistinctCountryBasedOnCourses(List<String> topSearchedCourseIds);

	public List<String> getCourseListForCourseBasedOnParameters(String courseId, String instituteId, String facultyId, String countryId,
			String cityId);

	public List<String> getCourseIdsForCountry(final String countryName);

	public List<String> getAllCoursesForCountry(List<String> otherCountryIds);

	public int updateCourseForCurrency(CurrencyRateDto currencyRate);

	public int getCountforNormalCourse(CourseSearchDto courseSearchDto, String searchKeyword, List<String> entityIds);

	public int getCountOfAdvanceSearch(List<String> entityIds, Object... values);

	public Integer getTotalCourseCountForInstitute(String instituteId);

	public List<CourseDTOElasticSearch> getUpdatedCourses(Date date, Integer startIndex, Integer limit);

	public Integer getCountOfTotalUpdatedCourses(Date utCdatetimeAsOnlyDate);

	public List<CourseDTOElasticSearch> getCoursesToBeRetriedForElasticSearch(List<String> courseIds, Integer startIndex, Integer limit);

	public void saveCourseIntake(CourseIntake courseIntake);

	public void deleteCourseIntake(String courseId);

	public List<CourseIntake> getCourseIntakeBasedOnCourseId(String courseId);

	public List<CourseIntake> getCourseIntakeBasedOnCourseIdList(List<String> courseIds);

	public void deleteCourseDeliveryMethod(String courseId);

	public void saveCourseLanguage(CourseLanguage courseLanguage);

	public void deleteCourseLanguage(String courseId);

	public List<CourseLanguage> getCourseLanguageBasedOnCourseId(String courseId);

	public List<String> getUserSearchCourseRecommendation(Integer startIndex, Integer pageSize, String searchKeyword);
	
	public Integer getCoursesCountBylevelId(String levelId);

	public int getDistinctCourseCountbyName(String courseName);

	public List<CourseResponseDto> getDistinctCourseListByName(Integer startIndex, Integer pageSize, String courseName);
	
	public List<CourseResponseDto> getNearestCourseForAdvanceSearch(AdvanceSearchDto courseSearchDto);
	
	public List<CourseResponseDto> getCourseByCountryName(Integer startIndex, Integer pageSize, String countryName);
	
	public Integer getTotalCountOfNearestCourses(Double latitude, Double longitude, Integer initialRadius);
}
