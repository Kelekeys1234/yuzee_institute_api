package com.seeka.freshfuture.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.freshfuture.app.bean.InstituteCourseFee;
import com.seeka.freshfuture.app.dao.IInstituteCourseFeeDAO;

@Service
@Transactional
public class InstituteCourseFeeService implements IInstituteCourseFeeService {
	
	@Autowired
	IInstituteCourseFeeDAO dao;
	
	@Override
	public void save(InstituteCourseFee obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(InstituteCourseFee obj) {
		dao.update(obj);
	}
	
	@Override
	public InstituteCourseFee get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public InstituteCourseFee getCourseFeeByInstitueAndCourse(Integer instituteId, Integer courseId) {
		return dao.getCourseFeeByInstitueAndCourse(instituteId,courseId);
	}
	 
	
}
