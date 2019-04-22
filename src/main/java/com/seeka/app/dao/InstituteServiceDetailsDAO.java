package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteServiceDetails;
import com.seeka.app.dto.InstituteSearchResultDto;

@Repository
public class InstituteServiceDetailsDAO implements IInstituteServiceDetailsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(InstituteServiceDetails obj) {	
		obj.setId(UUID.randomUUID());
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(InstituteServiceDetails obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public InstituteServiceDetails get(UUID id) {	
		Session session = sessionFactory.getCurrentSession();		
		InstituteServiceDetails obj = session.get(InstituteServiceDetails.class, id);
		return obj;
	}
	
	@Override
	public List<InstituteServiceDetails> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteServiceDetails.class); 
		return crit.list();
	}
	
	@Override
	public List<String> getAllServices(UUID instituteId) {
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createSQLQuery(
				"select distinct s.name from service s inner join institute_service i on s.id =i.service_id where i.institute_id = "+instituteId);
		List<String> rows = query.list();
		return rows;
	}
	
	/*@Override
	public Institute getUserByEmail(String email) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("emailId",email));
		List<User> users = crit.list();
		return users !=null && !users.isEmpty()?users.get(0):null;
	}*/
	
}
