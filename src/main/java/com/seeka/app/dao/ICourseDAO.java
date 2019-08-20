package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.Currency;
import com.seeka.app.bean.UserCompareCourse;
import com.seeka.app.bean.UserCompareCourseBundle;
import com.seeka.app.bean.YoutubeVideo;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;

public interface ICourseDAO {

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

    int findTotalCount();

    List<CourseRequest> getAll(Integer pageNumber, Integer pageSize);

    List<CourseRequest> searchCoursesBasedOnFilter(String query);

    List<CourseRequest> getUserCourse(BigInteger userId, Integer pageNumber, Integer pageSize, String currencyCode, String sortBy, boolean sortType);

    int findTotalCountByUserId(BigInteger userId);

    void saveUserCompareCourse(UserCompareCourse compareCourse);

    void saveUserCompareCourseBundle(UserCompareCourseBundle compareCourseBundle);

    List<UserCompareCourse> getUserCompareCourse(BigInteger userId);

    CourseRequest getCourseById(Integer valueOf);

    List<YoutubeVideo> getYoutubeDataforCourse(BigInteger instituteId, Set<String> keyword);

    Course getCourseData(BigInteger id);

    List<CourseResponseDto> advanceSearch(AdvanceSearchDto courseSearchDto);

}
