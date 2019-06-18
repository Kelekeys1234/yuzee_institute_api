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
    ICourseDAO dao;

    @Override
    public void save(Course obj) {
        dao.save(obj);
    }

    @Override
    public void update(Course obj) {
        dao.update(obj);
    }

    @Override
    public Course get(BigInteger id) {
        return dao.get(id);
    }

    @Override
    public List<Course> getAll() {
        return dao.getAll();
    }

    @Override
    public List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj, Currency currency, BigInteger userCountryId) {
        return dao.getAllCoursesByFilter(filterObj, currency, userCountryId);
    }

    @Override
    public CourseFilterCostResponseDto getAllCoursesFilterCostInfo(CourseSearchDto filterObj, Currency currency, String oldCurrencyCode) {
        return dao.getAllCoursesFilterCostInfo(filterObj, currency, oldCurrencyCode);
    }

    @Override
    public List<CourseResponseDto> getAllCoursesByInstitute(BigInteger instituteId, CourseSearchDto filterObj) {
        return dao.getAllCoursesByInstitute(instituteId, filterObj);
    }

    @Override
    public Map<String, Object> getCourse(BigInteger courseid) {
        return dao.getCourse(courseid);
    }

    @Override
    public List<CourseResponseDto> getCouresesByFacultyId(BigInteger facultyId) {
        return dao.getCouresesByFacultyId(facultyId);
    }

    @Override
    public List<CourseResponseDto> getCouresesByListOfFacultyId(String facultyId) {
        String[] citiesArray = facultyId.split(",");
        String tempList = "";
        for (String id : citiesArray) {
            tempList = tempList + "," + "'" + new BigInteger(id) + "'";
        }
        return dao.getCouresesByListOfFacultyId(tempList.substring(1, tempList.length()));
    }
}
