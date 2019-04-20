package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteDetails;
import com.seeka.app.bean.InstituteLevel;

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
	public InstituteLevel get(UUID id) {	
		Session session = sessionFactory.getCurrentSession();		
		InstituteLevel obj = session.get(InstituteLevel.class, id);
		return obj;
	}
	
	@Override
	public List<InstituteLevel> getAllLevelByInstituteId(UUID instituteId) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteLevel.class);
		crit.add(Restrictions.eq("instituteId",instituteId));
		return crit.list();
	}
	 
	
	
}
