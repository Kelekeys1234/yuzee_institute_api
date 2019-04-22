package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseGradeEligibility;

@Repository
public class CourseGradeEligibilityDAO implements ICourseGradeEligibilityDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(CourseGradeEligibility obj) {	
		obj.setId(UUID.randomUUID());
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(CourseGradeEligibility obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public CourseGradeEligibility get(UUID id) {	
		Session session = sessionFactory.getCurrentSession();		
		CourseGradeEligibility obj = session.get(CourseGradeEligibility.class, id);
		return obj;
	}
	
	@Override
	public List<CourseGradeEligibility> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Course.class); 
		return crit.list();
	}
	
	 
}
