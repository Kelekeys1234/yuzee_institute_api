package com.seeka.freshfuture.app.dao;

import java.util.List;

import com.seeka.freshfuture.app.bean.CoursePricing;

public interface ICoursePricingDAO {
	
	public void save(CoursePricing obj);
	public void update(CoursePricing obj);
	public CoursePricing get(Integer id);
	public List<CoursePricing> getAll();	
}
