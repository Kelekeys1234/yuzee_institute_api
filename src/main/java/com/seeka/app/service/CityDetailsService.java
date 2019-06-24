package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CityDetails;
import com.seeka.app.dao.ICityDetailsDAO;
 

@Service
@Transactional
public class CityDetailsService implements ICityDetailsService{
	
	@Autowired
	ICityDetailsDAO dao;

	@Override
	public List<CityDetails> getAll() {		
		return dao.getAll();
	}
	
	@Override
	public CityDetails get(BigInteger id) {
		return dao.get(id);
	}
	
	@Override
	public List<CityDetails> getAllCitiesByCountry(BigInteger countryId) {		
		return dao.getAllCitiesByCountry(countryId);
	}
	

}
