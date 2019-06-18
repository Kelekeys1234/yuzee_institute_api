package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteService;

@Repository
public class InstituteServiceDetailsDAO implements IInstituteServiceDetailsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(InstituteService obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(InstituteService obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public InstituteService get(BigInteger id) {	
		Session session = sessionFactory.getCurrentSession();		
		InstituteService obj = session.get(InstituteService.class, id);
		return obj;
	}
	
	@Override
	public List<InstituteService> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteService.class); 
		return crit.list();
	}
	
	@Override
	public List<String> getAllServices(BigInteger instituteId) {
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createSQLQuery(
				"select distinct s.name from service s inner join institute_service i on s.id =i.service_id where i.institute_id = '"+instituteId+"'");
		List<String> rows = query.list();
		return rows;
	}
	
	/*@Override
	public Institute getUserByEmail(String email) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(UserInfo.class);
		crit.add(Restrictions.eq("emailId",email));
		List<UserInfo> users = crit.list();
		return users !=null && !users.isEmpty()?users.get(0):null;
	}*/
	
}
