package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CountryImages;
import com.seeka.app.dao.ICountryImagesDAO;
 

@Service
@Transactional
public class CountryImagesService implements ICountryImagesService{
	
	@Autowired
	ICountryImagesDAO dao;
	
	public List<CountryImages> getAll() {
		// TO DO Auto-generated method stub
		return dao.getAll();
	}

}
