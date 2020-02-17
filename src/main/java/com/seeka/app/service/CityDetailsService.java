package com.seeka.app.service;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CityDetails;
import com.seeka.app.dao.ICityDetailsDAO;
 

@Service
@Transactional
public class CityDetailsService implements ICityDetailsService{
	
	@Autowired
	private ICityDetailsDAO iCityDetailsDAO;

	@Override
	public List<CityDetails> getAll() {		
		return iCityDetailsDAO.getAll();
	}
	
	@Override
	public CityDetails get(String id) {
		return iCityDetailsDAO.get(id);
	}
	
	@Override
	public List<CityDetails> getAllCitiesByCountry(String countryId) {		
		return iCityDetailsDAO.getAllCitiesByCountry(countryId);
	}
	

}
