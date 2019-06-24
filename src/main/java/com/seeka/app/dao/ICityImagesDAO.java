package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CityImages;

public interface ICityImagesDAO {
	public void save(CityImages obj);
	public void update(CityImages obj);
	public CityImages get(BigInteger id);
	public List<CityImages> getAll();	
}
