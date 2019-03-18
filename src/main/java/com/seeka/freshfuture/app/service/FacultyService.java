package com.seeka.freshfuture.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.freshfuture.app.bean.Faculty;
import com.seeka.freshfuture.app.dao.IFacultyDAO;

@Service
@Transactional
public class FacultyService implements IFacultyService {
	
	@Autowired
	IFacultyDAO dao;
	
	@Override
	public void save(Faculty obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(Faculty obj) {
		dao.update(obj);
	}
	
	@Override
	public Faculty get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public List<Faculty> getAll(){
		return dao.getAll();
	}
	
	@Override
	public List<Faculty> getFacultyByCountryIdAndCourseTypeId(Integer countryID,Integer courseTypeId){
		return dao.getFacultyByCountryIdAndCourseTypeId(countryID,courseTypeId);
	}
	
}
