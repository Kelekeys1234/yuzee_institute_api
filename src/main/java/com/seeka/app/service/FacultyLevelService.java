package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.FacultyLevel;
import com.seeka.app.dao.IFacultyLevelDAO;

@Service
@Transactional
public class FacultyLevelService implements IFacultyLevelService {
	
	@Autowired
	IFacultyLevelDAO dao;
	
	@Override
	public void save(FacultyLevel obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(FacultyLevel obj) {
		dao.update(obj);
	}
	
	@Override
	public FacultyLevel get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public List<FacultyLevel> getAll(){
		return dao.getAll();
	}
	
	@Override
	public List<FacultyLevel> getFacultyByCountryIdAndCourseTypeId(Integer countryID,Integer courseTypeId){
		return dao.getFacultyByCountryIdAndCourseTypeId(countryID,courseTypeId);
	}
	
}
