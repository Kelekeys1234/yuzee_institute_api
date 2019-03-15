package com.seeka.freshfuture.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.freshfuture.app.bean.SearchKeywords;

@Repository
public class SearchKeywordsDAO implements ISearchKeywordsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(SearchKeywords obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(SearchKeywords obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public List<SearchKeywords> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(SearchKeywords.class);
		return crit.list();
	}
	
	 
	
}
