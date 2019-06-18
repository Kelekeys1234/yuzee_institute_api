package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;
import java.util.Map;


import com.seeka.app.bean.Course;
import com.seeka.app.bean.Currency;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;

public interface ICourseDAO {

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
}
