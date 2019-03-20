package com.seeka.freshfuture.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.freshfuture.app.bean.InstituteServiceDetails;

@Repository
public class InstituteServiceDetailsDAO implements IInstituteServiceDetailsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(InstituteServiceDetails obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(InstituteServiceDetails obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public InstituteServiceDetails get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		InstituteServiceDetails obj = session.get(InstituteServiceDetails.class, id);
		return obj;
	}
	
	@Override
	public List<InstituteServiceDetails> getAllInstituteByCountry(Integer countryId) {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteServiceDetails.class);
		crit.add(Restrictions.eq("countryObj.id",countryId));
		return crit.list();
	}
	
	@Override
	public List<InstituteServiceDetails> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteServiceDetails.class); 
		return crit.list();
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
