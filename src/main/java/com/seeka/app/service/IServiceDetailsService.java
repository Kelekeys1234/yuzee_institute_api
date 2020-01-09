package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.Service;

public interface IServiceDetailsService {
	
	void save(Service obj);
	void update(Service obj);
	Service get(BigInteger id);
	List<Service> getAllInstituteByCountry(BigInteger countryId);
	List<Service> getAll();
}
