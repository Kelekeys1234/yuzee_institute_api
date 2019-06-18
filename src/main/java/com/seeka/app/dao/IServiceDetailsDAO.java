package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.Service;

public interface IServiceDetailsDAO {
	
	public void save(Service obj);
	public void update(Service obj);
	public Service get(BigInteger id);
	public List<Service> getAllInstituteByCountry(BigInteger countryId);	
	public List<Service> getAll();	
}
