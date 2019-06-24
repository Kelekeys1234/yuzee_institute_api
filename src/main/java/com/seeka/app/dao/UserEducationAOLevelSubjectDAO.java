package com.seeka.app.dao;import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserEducationAOLevelSubjects;
import com.seeka.app.bean.UserEnglishScore;

@Repository
public class UserEducationAOLevelSubjectDAO implements IUserEducationAOLevelSubjectDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(UserEducationAOLevelSubjects hobbiesObj) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(hobbiesObj);	   					
	}
	
	@Override
	public void update(UserEducationAOLevelSubjects hobbiesObj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(hobbiesObj);	   					
	}
	
	@Override
	public UserEducationAOLevelSubjects get(BigInteger id) {	
		Session session = sessionFactory.getCurrentSession();		
		UserEducationAOLevelSubjects obj = session.get(UserEducationAOLevelSubjects.class, id);
		return obj;
	}
	
	@Override
	public List<UserEducationAOLevelSubjects> getUserLevelSubjectGrades(BigInteger userId) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(UserEducationAOLevelSubjects.class);
		crit.add(Restrictions.eq("userId",userId));
		return crit.list();
	}
	
	@Override
	public List<UserEducationAOLevelSubjects> getActiveUserLevelSubjectGrades(BigInteger userId) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(UserEducationAOLevelSubjects.class);
		crit.add(Restrictions.eq("userId",userId)).add(Restrictions.eq("isActive",true));
		return crit.list();
	}
	
}
