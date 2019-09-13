package com.seeka.app.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Top10Course;

@Repository
public class Top10CourseDAO implements ITop10CourseDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final Top10Course top10Course) {
		Session session = sessionFactory.getCurrentSession();
		session.save(top10Course);
	}
	
	@Override
	public void deleteAll() {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("delete from top_10_courses").executeUpdate();
	}
}
