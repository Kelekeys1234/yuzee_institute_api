package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.ServiceDetails;

public interface IServiceDetailsDAO {
	
	public void save(ServiceDetails obj);
	public void update(ServiceDetails obj);
	public ServiceDetails get(UUID id);
	public List<ServiceDetails> getAllInstituteByCountry(UUID countryId);	
	public List<ServiceDetails> getAll();	
}
