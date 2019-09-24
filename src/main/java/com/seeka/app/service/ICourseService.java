package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Currency;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.YoutubeVideo;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseFilterDto;
import com.seeka.app.dto.CourseMinRequirementDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.UserCourse;
import com.seeka.app.dto.UserDto;
import com.seeka.app.exception.ValidationException;

public interface ICourseService {

	void save(Course obj);

	void update(Course obj);

	Course get(BigInteger id);

	List<Course> getAll();

	List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj);

	CourseFilterCostResponseDto getAllCoursesFilterCostInfo(CourseSearchDto filterObj, Currency currency, String oldCurrencyCode);

	List<CourseResponseDto> getAllCoursesByInstitute(BigInteger instituteId, CourseSearchDto filterObj);

	Map<String, Object> getCourse(BigInteger courseid);

	List<CourseResponseDto> getCouresesByFacultyId(BigInteger facultyId);

	List<CourseResponseDto> getCouresesByListOfFacultyId(String facultyId);

	Map<String, Object> save(@Valid CourseRequest courseDto);

	Map<String, Object> getAllCourse(Integer pageNumber, Integer pageSize);

	Map<String, Object> deleteCourse(@Valid BigInteger courseId);

	Map<String, Object> searchCourseBasedOnFilter(BigInteger countryId, BigInteger instituteId, BigInteger facultyId, String name, String languauge);

	Map<String, Object> addUserCourses(@Valid UserCourse userCourse);

	Map<String, Object> getUserCourse(BigInteger userId, Integer pageNumber, Integer pageSize, String currencyCode, String sortBy, Boolean sortAsscending);

	Map<String, Object> addUserCompareCourse(@Valid UserCourse userCourse);

	Map<String, Object> getUserCompareCourse(BigInteger userId);

	List<YoutubeVideo> getYoutubeDataforCourse(BigInteger courseId);

	List<YoutubeVideo> getYoutubeDataforCourse(BigInteger instituteId, String courseName);

	Course getCourseData(BigInteger courseId);

    Map<String, Object> getAllServices();

    Map<String, Object> advanceSearch(AdvanceSearchDto courseSearchDto);

    Map<String, Object> getAllCourse();

    Map<String, Object> update(@Valid CourseRequest courseDto, BigInteger id);

	Map<String, Object> courseFilter(CourseFilterDto courseFilter);

    Map<String, Object> autoSearch(Integer pageNumber, Integer pageSize, String searchKey);
    
    List<Course> facultyWiseCourseForInstitute(List<Faculty> facultyList, Institute institute);
    
    void saveCourseMinrequirement(CourseMinRequirementDto obj);
    
    CourseMinRequirementDto getCourseMinRequirement(BigInteger courseId);

    Map<String, Object> autoSearchByCharacter(String searchKey);
    
    long checkIfCoursesPresentForCountry(Country country);
    
    List<Course> getTopRatedCoursesForCountryWorldRankingWise(Country country);

	List<Course> getAllCourseUsingFaculty(Long facultyId);

	List<BigInteger> getAllCourseUsingFaculty(BigInteger facultyId);
	
	List<BigInteger> getTopSearchedCoursesByOtherUsers(BigInteger userId);

	List<Course> getCoursesById(List<BigInteger> allSearchCourses);

	Map<BigInteger, BigInteger> facultyWiseCourseIdMapForInstitute(List<Faculty> facultyList, BigInteger instituteId);

	List<Course> getAllCoursesUsingId(List<BigInteger> listOfRecommendedCourseIds);

	List<BigInteger> getTopRatedCourseIdForCountryWorldRankingWise(Country country);

	List<BigInteger> getTopSearchedCoursesByUsers(BigInteger userId);
	
	Set<Course> getRelatedCoursesBasedOnPastSearch(List<BigInteger> courseList) throws ValidationException;

	Long getCountOfDistinctInstitutesOfferingCoursesForCountry(UserDto userDto,Country country);
}
