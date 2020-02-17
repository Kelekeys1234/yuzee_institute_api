package com.seeka.app.dao;import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.City;
import com.seeka.app.bean.SeekaScholarships;

@Repository
public class SeekaScholarshipsDAO implements ISeekaScholarshipsDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(SeekaScholarships obj) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public List<SeekaScholarships> getAll() {		
		Session session = sessionFactory.getCurrentSession();		
		List<SeekaScholarships> list = session.createCriteria(City.class).list();	   					
		return list;
	}
	
	@Override
	public SeekaScholarships get(String id) {	
		Session session = sessionFactory.getCurrentSession();		
		SeekaScholarships city = session.get(SeekaScholarships.class, id);
		return city;
	}
	
}
