package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CityImages;

@Repository
public class CityImagesDAO implements ICityImagesDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(CityImages obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(CityImages obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public CityImages get(BigInteger id) {	
		Session session = sessionFactory.getCurrentSession();		
		CityImages obj = session.get(CityImages.class, id);
		return obj;
	}
	
	@Override
	public List<CityImages> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(CityImages.class); 
		return crit.list();
	}

}
