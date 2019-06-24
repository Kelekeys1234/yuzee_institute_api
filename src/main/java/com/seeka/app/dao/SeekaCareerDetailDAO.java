package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.SeekaCareerDetail;

@Repository
public class SeekaCareerDetailDAO implements ISeekaCareerDetailDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(SeekaCareerDetail obj) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public List<SeekaCareerDetail> getAll() {		
		Session session = sessionFactory.getCurrentSession();		
		List<SeekaCareerDetail> list = session.createCriteria(SeekaCareerDetail.class).list();	   					
		return list;
	}
	
	@Override
	public SeekaCareerDetail get(BigInteger id) {	
		Session session = sessionFactory.getCurrentSession();		
		SeekaCareerDetail city = session.get(SeekaCareerDetail.class, id);
		return city;
	}
		
}
