package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.CourseKeywords;

public interface ICourseKeywordService {

    public void save(CourseKeywords obj);

    public void update(CourseKeywords obj);

    public List<CourseKeywords> getAll();

    public List<CourseKeywords> searchCourseKeyword(String keyword);
}
