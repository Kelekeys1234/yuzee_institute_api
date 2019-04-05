package com.seeka.app.service;

import java.util.List;

import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Level;
import com.seeka.app.dao.ILevelDAO;

@Service
@Transactional
public class LevelService implements ILevelService {
	
	@Autowired
	ILevelDAO dao;
	
	@Override
	public void save(Level obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(Level obj) {
		dao.update(obj);
	}
	
	@Override
	public Level get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public List<Level> getAll(){
		return dao.getAll();
	}
	
	@Override
	public List<Level> getCourseTypeByCountryId(Integer countryID){
		return dao.getCourseTypeByCountryId(countryID);
	}
	
	@Override
	public List<Level> getLevelByCountryId(Integer countryId){
		return dao.getLevelByCountryId(countryId);
	}
	
	@Override
	public List<Level> getAllLevelByCountry(){
		return dao.getAllLevelByCountry();
	}
}
