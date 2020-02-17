package com.seeka.app.dao;import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CourseDetails;

@Repository
public class CourseDetailsDAO implements ICourseDetailsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(CourseDetails obj) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(CourseDetails obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public CourseDetails get(String id) {	
		Session session = sessionFactory.getCurrentSession();		
		CourseDetails obj = session.get(CourseDetails.class, id);
		return obj;
	}
	
	@Override
	public List<CourseDetails> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(CourseDetails.class); 
		return crit.list();
	}
	
	 
}
