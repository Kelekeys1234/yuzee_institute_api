package com.seeka.app.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.seeka.app.bean.Course;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;

public interface ICourseService {
	
	public void save(Course obj);
	public void update(Course obj);
	public Course get(UUID id);
	public List<Course> getAll(); 
	public List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj);
	public List<CourseResponseDto> getAllCoursesByInstitute(UUID instituteId, CourseSearchDto filterObj);
	public Map<String, Object> getCourse(UUID courseid);
}
