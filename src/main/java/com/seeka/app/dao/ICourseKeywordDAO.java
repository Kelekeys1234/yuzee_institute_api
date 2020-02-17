package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.CourseKeywords;

public interface ICourseKeywordDAO {

    public void save(CourseKeywords obj);

    public void update(CourseKeywords obj);

    public List<CourseKeywords> getAll();

    public List<CourseKeywords> searchCourseKeyword(String keyword);

}
