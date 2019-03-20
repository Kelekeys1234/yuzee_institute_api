package com.seeka.freshfuture.app.service;

import java.util.List;

import com.seeka.freshfuture.app.bean.CoursePricing;

public interface ICoursePricingService {
	
	public void save(CoursePricing obj);
	public void update(CoursePricing obj);
	public CoursePricing get(Integer id);
	public List<CoursePricing> getAll(); 
}
