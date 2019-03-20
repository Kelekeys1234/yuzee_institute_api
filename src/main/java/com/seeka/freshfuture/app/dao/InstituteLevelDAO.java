package com.seeka.freshfuture.app.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.freshfuture.app.bean.InstituteLevel;

@Repository
public class InstituteLevelDAO implements IInstituteLevelDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(InstituteLevel obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(InstituteLevel obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public InstituteLevel get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		InstituteLevel obj = session.get(InstituteLevel.class, id);
		return obj;
	}
	 
	
	
}
