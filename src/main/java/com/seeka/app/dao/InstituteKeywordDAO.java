package com.seeka.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteKeyword;

@Repository
public class InstituteKeywordDAO implements IInstituteKeywordDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(InstituteKeyword obj) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(InstituteKeyword obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public List<InstituteKeyword> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteKeyword.class);
		return crit.list();
	}
	
	/*@Override
	public List<InstituteKeyword> searchCourseKeyword(String keyword){
		Session session = sessionFactory.getCurrentSession();		
		Query query = session.createSQLQuery("SELECT sk.keyword FROM search_keywords sk WHERE sk.keyword LIKE '%"+keyword+"%'");
		return query.list();
	}*/
	
}
