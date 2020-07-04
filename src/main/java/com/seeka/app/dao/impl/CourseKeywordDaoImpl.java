package com.seeka.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.CourseKeywords;
import com.seeka.app.dao.CourseKeywordDao;
import com.seeka.app.repository.CourseKeywordRepository;

@Component
public class CourseKeywordDaoImpl implements CourseKeywordDao {

    @Autowired
    private CourseKeywordRepository courseKeywordRepository;

    @Override
    public void save(CourseKeywords courseKeywords) {
    	courseKeywordRepository.save(courseKeywords);
    }

    @Override
    public void update(CourseKeywords courseKeywords) {
    	courseKeywordRepository.save(courseKeywords);
    }

    @Override
    public List<CourseKeywords> getAll() {
        return courseKeywordRepository.findAll();
    }

    @Override
    public List<CourseKeywords> searchCourseKeyword(String keyword) {
        return courseKeywordRepository.findByKeywordContaining(keyword);
    }
}
