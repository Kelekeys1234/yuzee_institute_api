package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.Currency;
import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;

@Service
@Transactional
public class CourseService implements ICourseService {

    @Autowired
    private ICourseDAO iCourseDAO;

    @Override
    public void save(Course course) {
        iCourseDAO.save(course);
    }

    @Override
    public void update(Course course) {
        iCourseDAO.update(course);
    }

    @Override
    public Course get(BigInteger id) {
        return iCourseDAO.get(id);
    }

    @Override
    public List<Course> getAll() {
        return iCourseDAO.getAll();
    }

    @Override
    public List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto courseSearchDto, Currency currency, BigInteger userCountryId) {
        return iCourseDAO.getAllCoursesByFilter(courseSearchDto, currency, userCountryId);
    }

    @Override
    public CourseFilterCostResponseDto getAllCoursesFilterCostInfo(CourseSearchDto courseSearchDto, Currency currency, String oldCurrencyCode) {
        return iCourseDAO.getAllCoursesFilterCostInfo(courseSearchDto, currency, oldCurrencyCode);
    }

    @Override
    public List<CourseResponseDto> getAllCoursesByInstitute(BigInteger instituteId, CourseSearchDto courseSearchDto) {
        return iCourseDAO.getAllCoursesByInstitute(instituteId, courseSearchDto);
    }

    @Override
    public Map<String, Object> getCourse(BigInteger courseId) {
        return iCourseDAO.getCourse(courseId);
    }

    @Override
    public List<CourseResponseDto> getCouresesByFacultyId(BigInteger facultyId) {
        return iCourseDAO.getCouresesByFacultyId(facultyId);
    }

    @Override
    public List<CourseResponseDto> getCouresesByListOfFacultyId(String facultyId) {
        String[] citiesArray = facultyId.split(",");
        String tempList = "";
        for (String id : citiesArray) {
            tempList = tempList + "," + "'" + new BigInteger(id) + "'";
        }
        return iCourseDAO.getCouresesByListOfFacultyId(tempList.substring(1, tempList.length()));
    }
}
