package com.yuzee.app.dao;import java.util.List;

import com.yuzee.app.bean.CourseKeywords;

public interface CourseKeywordDao {

    public void save(CourseKeywords obj);

    public void saveAll(List<CourseKeywords> list);

    public void update(CourseKeywords obj);

    public List<CourseKeywords> getAll();

    public List<CourseKeywords> searchCourseKeyword(String keyword);

}
