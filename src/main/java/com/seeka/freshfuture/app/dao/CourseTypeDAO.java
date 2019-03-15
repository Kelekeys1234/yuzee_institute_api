package com.seeka.freshfuture.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.freshfuture.app.bean.CourseType;

@Repository
public class CourseTypeDAO implements ICourseTypeDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(CourseType obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(CourseType obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public CourseType get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		CourseType obj = session.get(CourseType.class, id);
		return obj;
	}
	
	@Override
	public List<CourseType> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(CourseType.class);
		return crit.list();
	}
 
	
}
