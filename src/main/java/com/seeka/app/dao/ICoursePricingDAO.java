package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.CoursePricing;

public interface ICoursePricingDAO {
	
	public void save(CoursePricing obj);
	public void update(CoursePricing obj);
	public CoursePricing get(UUID id);
	public CoursePricing getPricingByCourseId(UUID courseId);
	public List<CoursePricing> getAll(); 
}
