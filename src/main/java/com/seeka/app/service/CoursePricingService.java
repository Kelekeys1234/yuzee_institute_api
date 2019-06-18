package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CoursePricing;
import com.seeka.app.dao.ICoursePricingDAO;

@Service
@Transactional
public class CoursePricingService implements ICoursePricingService {
	
	@Autowired
	ICoursePricingDAO dao;
	
	@Override
	public void save(CoursePricing obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(CoursePricing obj) {
		dao.update(obj);
	}
	
	@Override
	public CoursePricing get(BigInteger id) {
		return dao.get(id);
	}
	
	@Override
	public List<CoursePricing> getAll(){
		return dao.getAll();
	} 
	
	@Override
	public CoursePricing getPricingByCourseId(BigInteger courseId) {
		return dao.getPricingByCourseId(courseId);
	}

}
