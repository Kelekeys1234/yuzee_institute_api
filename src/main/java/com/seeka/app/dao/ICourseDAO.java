package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Course;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;

public interface ICourseDAO {
	
	public void save(Course obj);
	public void update(Course obj);
	public Course get(Integer id);
	public List<Course> getAll();
	public List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj);
}
