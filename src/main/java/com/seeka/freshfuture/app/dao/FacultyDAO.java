package com.seeka.freshfuture.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.freshfuture.app.bean.Faculty;
import com.seeka.freshfuture.app.bean.Institute;

@Repository
public class FacultyDAO implements IFacultyDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(Faculty obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(Faculty obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public Faculty get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		Faculty obj = session.get(Faculty.class, id);
		return obj;
	}
	
	@Override
	public List<Faculty> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Institute.class);
		return crit.list();
	} 
	
}
