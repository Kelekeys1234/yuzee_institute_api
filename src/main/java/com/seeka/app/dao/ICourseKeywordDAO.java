package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.CourseKeyword;

public interface ICourseKeywordDAO {

    public void save(CourseKeyword obj);

    public void update(CourseKeyword obj);

    public List<CourseKeyword> getAll();

    public List<CourseKeyword> searchCourseKeyword(String keyword);

}
