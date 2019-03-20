package com.seeka.freshfuture.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.freshfuture.app.bean.Level;

@Repository
public class LevelDAO implements ILevelDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(Level obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(Level obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public Level get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		Level obj = session.get(Level.class, id);
		return obj;
	}
	
	@Override
	public List<Level> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Level.class);
		return crit.list();
	}
	
	@Override
	public List<Level> getCourseTypeByCountryId(Integer countryID) {
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createSQLQuery(
				"select distinct ct.id, ct.type_txt as courseType from course_type ct with(nolock) inner join faculty f with(nolock) on f.course_type_id = ct.id "
				+ "inner join course c with(nolock) on c.faculty_id = f.id inner join institute_course ic with(nolock) on ic.course_id = c.id "
				+ "where ic.country_id = :countryId")
				.setParameter("countryId", countryID);
		List<Object[]> rows = query.list();
		List<Level> courseTypes = new ArrayList<>();
		for(Object[] row : rows){
			Level obj = new Level();
			obj.setId(Integer.parseInt(row[0].toString()));
			obj.setName(row[1].toString());
			courseTypes.add(obj);
		}
		return courseTypes;
	}
 
	
}
