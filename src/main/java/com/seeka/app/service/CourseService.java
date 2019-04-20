package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Course;
import com.seeka.app.dao.ICourseDAO;
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
	public Course get(UUID id) {
		return dao.get(id);
	}
	
	@Override
	public List<Course> getAll(){
		return dao.getAll();
	} 
	
	@Override
	public List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj){
		return dao.getAllCoursesByFilter(filterObj);
	}
	
	@Override
	public List<CourseResponseDto> getAllCoursesByInstitute(UUID instituteId, CourseSearchDto filterObj){
		return dao.getAllCoursesByInstitute(instituteId,filterObj);
	}
	
}
