package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserInfo;
import com.seeka.app.enumeration.SignUpType;

@Repository
public class UserDAO implements IUserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(UserInfo user) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(user);	   					
	}
	
	@Override
	public void update(UserInfo user) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(user);	   					
	}
	
	@Override
	public UserInfo get(BigInteger userId) {	
		Session session = sessionFactory.getCurrentSession();		
		UserInfo user = session.get(UserInfo.class, userId);
		return user;
	}
	
	@Override
	public UserInfo getUserByEmail(String email) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(UserInfo.class);
		crit.add(Restrictions.eq("emailId",email));
		List<UserInfo> users = crit.list();
		return users !=null && !users.isEmpty()?users.get(0):null;
	}
	
	@Override
	public UserInfo getUserBySocialAccountId(String accountId, SignUpType signUpType) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(UserInfo.class);
		crit.add(Restrictions.eq("signUpType",signUpType));
		crit.add(Restrictions.eq("socialAccountId",accountId));
		List<UserInfo> users = crit.list();
		return users !=null && !users.isEmpty()?users.get(0):null;
	}

}
