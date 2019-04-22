package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteImages;

@Repository
public class InstituteImagesDAO implements IInstituteImagesDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(InstituteImages obj) {	
		obj.setId(UUID.randomUUID());
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(InstituteImages obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public InstituteImages get(UUID id) {	
		Session session = sessionFactory.getCurrentSession();		
		InstituteImages obj = session.get(InstituteImages.class, id);
		return obj;
	}
	
	@Override
	public List<InstituteImages> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteImages.class); 
		return crit.list();
	}

}
