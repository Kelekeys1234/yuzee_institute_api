package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.CoursePricing;

public interface ICoursePricingService {
	
	public void save(CoursePricing obj);
	public void update(CoursePricing obj);
	public CoursePricing get(UUID id);
	public List<CoursePricing> getAll(); 
	public CoursePricing getPricingByCourseId(UUID courseId);
}
