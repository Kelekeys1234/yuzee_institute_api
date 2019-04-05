package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Faculty;
import com.seeka.app.dao.IFacultyDAO;

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
	public List<Faculty> getFacultyByCountryIdAndLevelId(Integer countryID,Integer levelId){
		return dao.getFacultyByCountryIdAndLevelId(countryID,levelId);
	}
	
	@Override
	public List<Faculty> getAllFacultyByCountryIdAndLevel(){
		return dao.getAllFacultyByCountryIdAndLevel();
	}
	
}
