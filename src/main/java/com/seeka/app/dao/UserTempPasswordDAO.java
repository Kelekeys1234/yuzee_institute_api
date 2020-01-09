package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserTempPassword;

@Repository
public class UserTempPasswordDAO implements IUserTempPasswordDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(UserTempPassword obj) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(UserTempPassword obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public UserTempPassword get(BigInteger userId) {	
		Session session = sessionFactory.getCurrentSession(); 	
		UserTempPassword UserTempPassword = session.get(UserTempPassword.class, userId);
		return UserTempPassword;
	}
	
	@Override
	public UserTempPassword getUserByEmail(String email) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(UserTempPassword.class);
		crit.add(Restrictions.eq("emailId",email)).add(Restrictions.eq("status", "ACTIVE"));;
		List<UserTempPassword> users = crit.list();
		return users !=null && !users.isEmpty()?users.get(0):null;
	}
	
	@Override
	public List<UserTempPassword> getActiveTempPasswordUserId(BigInteger userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserTempPassword.class);
		crit.add(Restrictions.eq("userId", userId)).add(Restrictions.eq("status", "ACTIVE"));
		List<UserTempPassword> passwordList = crit.list();
		return passwordList;
	}
	

}
