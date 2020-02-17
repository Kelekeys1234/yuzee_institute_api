package com.seeka.app.dao;import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.SeekaPopup;

@Repository
public class SeekaPopupDAO implements ISeekaPopupDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(SeekaPopup obj) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public List<SeekaPopup> getAll() {		
		Session session = sessionFactory.getCurrentSession();		
		List<SeekaPopup> list = session.createCriteria(SeekaPopup.class).list();	   					
		return list;
	}
	
	@Override
	public SeekaPopup get(String id) {	
		Session session = sessionFactory.getCurrentSession();		
		SeekaPopup city = session.get(SeekaPopup.class, id);
		return city;
	}
	
	
}
