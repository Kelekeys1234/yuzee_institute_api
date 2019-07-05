package com.seeka.app.service;import java.math.BigInteger;

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
	private ICurrencyDAO dao;
	
	@Override
	public void save(Currency obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(Currency obj) {
		dao.update(obj);
	}
	
	@Override
	public Currency get(BigInteger id) {
		return dao.get(id);
	}
	
	@Override
	public List<Currency> getAll(){
		return dao.getAll();
	}
	
	@Override
	public List<Currency> getCourseTypeByCountryId(BigInteger countryID){
		return dao.getCourseTypeByCountryId(countryID);
	}
	
	@Override
	public List<Currency> getCurrencyByCountryId(BigInteger countryId){
		return dao.getCurrencyByCountryId(countryId);
	}
	
	@Override
	public Currency getCurrencyByCode(String currencyCode) {
		return dao.getCurrencyByCode(currencyCode);
	}
}
