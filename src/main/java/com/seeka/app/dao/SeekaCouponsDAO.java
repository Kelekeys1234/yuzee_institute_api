package com.seeka.app.dao;import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.SeekaCoupons;

@Repository
public class SeekaCouponsDAO implements ISeekaCouponsDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(SeekaCoupons obj) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public List<SeekaCoupons> getAll() {		
		Session session = sessionFactory.getCurrentSession();		
		List<SeekaCoupons> list = session.createCriteria(SeekaCoupons.class).list();	   					
		return list;
	}
	
	@Override
	public SeekaCoupons get(BigInteger id) {	
		Session session = sessionFactory.getCurrentSession();		
		SeekaCoupons seekaCoupons = session.get(SeekaCoupons.class, id);
		return seekaCoupons;
	}
	

	
}
