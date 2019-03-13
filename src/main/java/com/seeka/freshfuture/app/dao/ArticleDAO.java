package com.seeka.freshfuture.app.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.seeka.freshfuture.app.bean.Article;
@Repository
public class ArticleDAO implements IArticleDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Article> getAll() {
		
		Session session = sessionFactory.getCurrentSession();		
		@SuppressWarnings("unchecked")
		List<Article> list = session.createCriteria(Article.class).list();	   					
		return list;
		
	}
	
	
}
