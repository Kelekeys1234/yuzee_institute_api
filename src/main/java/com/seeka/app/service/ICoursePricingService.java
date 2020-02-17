package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.CoursePricing;

public interface ICoursePricingService {
	
	public void save(CoursePricing obj);
	public void update(CoursePricing obj);
	public CoursePricing get(String id);
	public List<CoursePricing> getAll(); 
	public CoursePricing getPricingByCourseId(String courseId);
}
