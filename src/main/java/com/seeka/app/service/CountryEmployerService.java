/*package com.seeka.app.service;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CountryEmployers;
import com.seeka.app.dao.ICountryEmployerDAO;
import com.seeka.app.dto.CountryDto;
 

@Service
@Transactional
public class CountryEmployerService implements ICountryEmployerService{
	
	@Autowired
	private ICountryEmployerDAO iCountryEmployerDAO;
	
	@Override
	public void save(CountryEmployers obj) {
		iCountryEmployerDAO.save(obj);
		
	}
	
	@Override
	public List<CountryEmployers> getAll() {		
		return iCountryEmployerDAO.getAll();
	}
    
	@Override
	public CountryEmployers get(String id) {
		return iCountryEmployerDAO.get(id);
	}
	
	@Override
	public List<CountryDto> getAllUniversityCountries(){
		return iCountryEmployerDAO.getAllUniversityCountries();
	}
	
	@Override
	public List<CountryDto> searchInterestByCountry(String name) {		
		return iCountryEmployerDAO.searchInterestByCountry(name);
	}
	
	@Override
	public List<CountryDto> getAllCountries(){
		return iCountryEmployerDAO.getAllCountries();
	}
}
*/