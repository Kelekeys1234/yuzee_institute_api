package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.Service;

public interface IServiceDetailsService {
	
	public void save(Service obj);
	public void update(Service obj);
	public Service get(BigInteger id);
	public List<Service> getAllInstituteByCountry(BigInteger countryId);
	public List<Service> getAll();
}
