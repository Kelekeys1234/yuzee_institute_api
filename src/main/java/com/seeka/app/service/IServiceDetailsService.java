package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.Service;

public interface IServiceDetailsService {
	
	void save(Service obj);
	void update(Service obj);
	Service get(String id);
	List<Service> getAllInstituteByCountry(String countryId);
	List<Service> getAll();
}
