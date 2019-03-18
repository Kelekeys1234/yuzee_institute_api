package com.seeka.freshfuture.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.freshfuture.app.bean.CourseType;
import com.seeka.freshfuture.app.dao.ICourseTypeDAO;

@Service
@Transactional
public class CourseTypeService implements ICourseTypeService {
	
	@Autowired
	ICourseTypeDAO dao;
	
	@Override
	public void save(CourseType obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(CourseType obj) {
		dao.update(obj);
	}
	
	@Override
	public CourseType get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public List<CourseType> getAll(){
		return dao.getAll();
	}
	
	@Override
	public List<CourseType> getCourseTypeByCountryId(Integer countryID){
		return dao.getCourseTypeByCountryId(countryID);
	}
	
}
