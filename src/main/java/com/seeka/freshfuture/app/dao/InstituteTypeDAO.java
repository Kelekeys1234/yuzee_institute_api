package com.seeka.freshfuture.app.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.freshfuture.app.bean.InstituteType;

@Repository
public class InstituteTypeDAO implements IInstituteTypeDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(InstituteType obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(InstituteType obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public InstituteType get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		InstituteType obj = session.get(InstituteType.class, id);
		return obj;
	}
	 
	
	
}
