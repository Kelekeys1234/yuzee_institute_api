package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Service;

@Repository
public class ServiceDetailsDAO implements IServiceDetailsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Service obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(Service obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public Service get(BigInteger id) {	
		Session session = sessionFactory.getCurrentSession();		
		Service obj = session.get(Service.class, id);
		return obj;
	}
	
	@Override
	public List<Service> getAllInstituteByCountry(BigInteger countryId) {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Service.class);
		crit.add(Restrictions.eq("countryObj.id",countryId));
		return crit.list();
	}
	
	@Override
	public List<Service> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Service.class).addOrder(Order.asc("name")); 
		return crit.list();
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
