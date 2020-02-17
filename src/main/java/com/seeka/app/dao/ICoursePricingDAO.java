package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.CoursePricing;

public interface ICoursePricingDAO {
	
	public void save(CoursePricing obj);
	public void update(CoursePricing obj);
	public CoursePricing get(String id);
	public CoursePricing getPricingByCourseId(String courseId);
	public List<CoursePricing> getAll(); 
}
