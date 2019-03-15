package com.seeka.freshfuture.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.freshfuture.app.bean.InstituteCourse;

@Repository
public class InstituteCourseDAO implements IInstituteCourseDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(InstituteCourse obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(InstituteCourse obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public InstituteCourse get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		InstituteCourse obj = session.get(InstituteCourse.class, id);
		return obj;
	}
	
	@Override
	public List<InstituteCourse> getAllCoursesByInstitute(Integer instituteId) {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteCourse.class);
		crit.add(Restrictions.eq("instId",instituteId));
		return crit.list();
	}
	
	@Override
	public InstituteCourse getCourseByInstitueAndCourse(Integer instituteId, Integer courseId) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteCourse.class);
		crit.add(Restrictions.eq("instId",instituteId));
		crit.add(Restrictions.eq("courseId",courseId));
		List<InstituteCourse> list = crit.list();
		return list !=null && !list.isEmpty()?list.get(0):null;
	}
	
}
