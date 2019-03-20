package com.seeka.freshfuture.app.service;

import java.util.List;

import com.seeka.freshfuture.app.bean.ServiceDetails;

public interface IServiceDetailsService {
	
	public void save(ServiceDetails obj);
	public void update(ServiceDetails obj);
	public ServiceDetails get(Integer id);
	public List<ServiceDetails> getAllInstituteByCountry(Integer countryId);
	public List<ServiceDetails> getAll();
}
