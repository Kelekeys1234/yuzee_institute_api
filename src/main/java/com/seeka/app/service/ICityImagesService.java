package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CityImages;

public interface ICityImagesService {
	
	public void save(CityImages obj);
	public void update(CityImages obj);
	public CityImages get(BigInteger id);
	public List<CityImages> getAll(); 
}
