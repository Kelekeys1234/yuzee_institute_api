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
	private ICoursePricingDAO iCoursePricingDAO;
	
	@Override
	public void save(CoursePricing coursePricing) {
		iCoursePricingDAO.save(coursePricing);
	}
	
	@Override
	public void update(CoursePricing coursePricing) {
		iCoursePricingDAO.update(coursePricing);
	}
	
	@Override
	public CoursePricing get(BigInteger id) {
		return iCoursePricingDAO.get(id);
	}
	
	@Override
	public List<CoursePricing> getAll(){
		return iCoursePricingDAO.getAll();
	} 
	
	@Override
	public CoursePricing getPricingByCourseId(BigInteger courseId) {
		return iCoursePricingDAO.getPricingByCourseId(courseId);
	}

}
