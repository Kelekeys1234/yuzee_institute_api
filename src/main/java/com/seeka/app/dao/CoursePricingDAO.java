package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CoursePricing;

@Repository
public class CoursePricingDAO implements ICoursePricingDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(CoursePricing obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(CoursePricing obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public CoursePricing get(BigInteger id) {	
		Session session = sessionFactory.getCurrentSession();		
		CoursePricing obj = session.get(CoursePricing.class, id);
		return obj;
	}
	
	@Override
	public List<CoursePricing> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Course.class); 
		return crit.list();
	}
	@Override
	public CoursePricing getPricingByCourseId(BigInteger courseId) {		
		Session session = sessionFactory.getCurrentSession();				 
		Criteria criteria = session.createCriteria(CoursePricing.class);	   	
		criteria.add(Restrictions.eq("courseId", courseId));
		return (CoursePricing) criteria.list().get(0);
	}
	
	 
}
