package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CoursePricing;

public interface ICoursePricingDAO {
	
	public void save(CoursePricing obj);
	public void update(CoursePricing obj);
	public CoursePricing get(BigInteger id);
	public CoursePricing getPricingByCourseId(BigInteger courseId);
	public List<CoursePricing> getAll(); 
}
