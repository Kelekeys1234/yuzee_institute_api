package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserEnglishScore;

@Repository
public class UserEnglishScoreDAO implements IUserEnglishScoreDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(UserEnglishScore obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}
	
	@Override
	public void update(UserEnglishScore obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}

	@Override
	public List<UserEnglishScore> getAll() {
		System.out.println("Inside UserEnglishScore DAO....");
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<UserEnglishScore> list = session.createCriteria(UserEnglishScore.class).list();
		return list;
	}

	@Override
	public UserEnglishScore get(BigInteger id) {
		System.out.println("BigInteger : "+id);
		Session session = sessionFactory.getCurrentSession();		
		UserEnglishScore UserEnglishScore = session.get(UserEnglishScore.class, id);
		return UserEnglishScore;
	}

	@Override
	public List<UserEnglishScore> getEnglishEligibiltyByUserID(BigInteger userId) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(UserEnglishScore.class);
		crit.add(Restrictions.eq("userId",userId));
		return crit.list();
	}
 
}
