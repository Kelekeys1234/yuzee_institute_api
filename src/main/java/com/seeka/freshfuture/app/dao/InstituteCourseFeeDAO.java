package com.seeka.freshfuture.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.freshfuture.app.bean.InstituteCourse;
import com.seeka.freshfuture.app.bean.InstituteCourseFee;

@Repository
public class InstituteCourseFeeDAO implements IInstituteCourseFeeDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(InstituteCourseFee obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(InstituteCourseFee obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public InstituteCourseFee get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		InstituteCourseFee obj = session.get(InstituteCourseFee.class, id);
		return obj;
	}
	
	
	@Override
	public InstituteCourseFee getCourseFeeByInstitueAndCourse(Integer instituteId, Integer courseId) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteCourse.class);
		crit.add(Restrictions.eq("instId",instituteId));
		crit.add(Restrictions.eq("courseId",courseId));
		List<InstituteCourseFee> list = crit.list();
		return list !=null && !list.isEmpty()?list.get(0):null;
	}
	
}
