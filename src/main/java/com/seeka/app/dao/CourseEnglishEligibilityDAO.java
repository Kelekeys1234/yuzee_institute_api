package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseEnglishEligibility;

@Repository
public class CourseEnglishEligibilityDAO implements ICourseEnglishEligibilityDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(CourseEnglishEligibility obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(CourseEnglishEligibility obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public CourseEnglishEligibility get(UUID id) {	
		Session session = sessionFactory.getCurrentSession();		
		CourseEnglishEligibility obj = session.get(CourseEnglishEligibility.class, id);
		return obj;
	}
	
	@Override
	public List<CourseEnglishEligibility> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Course.class); 
		return crit.list();
	}
	
	@Override
	public List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(UUID courseID) {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(CourseEnglishEligibility.class); 
		crit.add(Restrictions.eq("courseId",courseID));
		return crit.list();
	}
	
	 
}
