package com.seeka.freshfuture.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.freshfuture.app.bean.InstituteCourse;
import com.seeka.freshfuture.app.dao.IInstituteCourseDAO;

@Service
@Transactional
public class InstituteCourseService implements IInstituteCourseService {
	
	@Autowired
	IInstituteCourseDAO dao;
	
	@Override
	public void save(InstituteCourse obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(InstituteCourse obj) {
		dao.update(obj);
	}
	
	@Override
	public InstituteCourse get(Integer id) {
		return dao.get(id);
	}
	 
	@Override
	public List<InstituteCourse> getAllCoursesByInstitute(Integer instituteId){
		return dao.getAllCoursesByInstitute(instituteId);
	}
	
	@Override
	public InstituteCourse getCourseByInstitueAndCourse(Integer instituteId, Integer courseId) {
		return dao.getCourseByInstitueAndCourse(instituteId,courseId);
	}
	
}
