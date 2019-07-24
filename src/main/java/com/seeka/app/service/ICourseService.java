package com.seeka.app.service;import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.Currency;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.UserCourse;

public interface ICourseService {

    public void save(Course obj);

    public void update(Course obj);

    public Course get(BigInteger id);

    public List<Course> getAll();

    public List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj, Currency currency, BigInteger userCountryId);

    public CourseFilterCostResponseDto getAllCoursesFilterCostInfo(CourseSearchDto filterObj, Currency currency, String oldCurrencyCode);

    public List<CourseResponseDto> getAllCoursesByInstitute(BigInteger instituteId, CourseSearchDto filterObj);

    public Map<String, Object> getCourse(BigInteger courseid);

    public List<CourseResponseDto> getCouresesByFacultyId(BigInteger facultyId);

    public List<CourseResponseDto> getCouresesByListOfFacultyId(String facultyId);

    public Map<String, Object> save(@Valid CourseRequest courseDto);

    public Map<String, Object> getAllCourse(Integer pageNumber, Integer pageSize);

    public Map<String, Object> deleteCourse(@Valid BigInteger courseId);

    public Map<String, Object> searchCourseBasedOnFilter(BigInteger countryId, BigInteger instituteId, BigInteger facultyId, String name, String languauge);

    public Map<String, Object> addUserCourses(@Valid UserCourse userCourse);

    public Map<String, Object> getUserCourse(BigInteger userId, Integer pageNumber, Integer pageSize);
}
