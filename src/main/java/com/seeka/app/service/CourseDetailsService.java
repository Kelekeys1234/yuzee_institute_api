package com.seeka.app.service;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CourseDetails;
import com.seeka.app.dao.ICourseDetailsDAO;

@Service
@Transactional
public class CourseDetailsService implements ICourseDetailsService {
	
	@Autowired
	private ICourseDetailsDAO iCourseDetailsDAO;
	
	@Override
	public void save(CourseDetails obj) {
		iCourseDetailsDAO.save(obj);
	}
	
	@Override
	public void update(CourseDetails obj) {
		iCourseDetailsDAO.update(obj);
	}
	
	@Override
	public CourseDetails get(String id) {
		return iCourseDetailsDAO.get(id);
	}
	
	@Override
	public List<CourseDetails> getAll(){
		return iCourseDetailsDAO.getAll();
	} 
	
}
