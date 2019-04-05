package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Currency;
import com.seeka.app.dao.ICurrencyDAO;

@Service
@Transactional
public class CurrencyService implements ICurrencyService {
	
	@Autowired
	ICurrencyDAO dao;
	
	@Override
	public void save(Currency obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(Currency obj) {
		dao.update(obj);
	}
	
	@Override
	public Currency get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public List<Currency> getAll(){
		return dao.getAll();
	}
	
	@Override
	public List<Currency> getCourseTypeByCountryId(Integer countryID){
		return dao.getCourseTypeByCountryId(countryID);
	}
	
	@Override
	public List<Currency> getCurrencyByCountryId(Integer countryId){
		return dao.getCurrencyByCountryId(countryId);
	}
}
