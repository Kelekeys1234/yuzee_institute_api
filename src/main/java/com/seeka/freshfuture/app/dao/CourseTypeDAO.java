package com.seeka.freshfuture.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.freshfuture.app.bean.CourseType;

@Repository
public class CourseTypeDAO implements ICourseTypeDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(CourseType obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(CourseType obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public CourseType get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		CourseType obj = session.get(CourseType.class, id);
		return obj;
	}
	
	@Override
	public List<CourseType> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(CourseType.class);
		return crit.list();
	}
	
	@Override
	public List<CourseType> getCourseTypeByCountryId(Integer countryID) {
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createSQLQuery(
				"select ct.id, ct.type_txt as courseType from course_type ct with(nolock) inner join faculty f with(nolock) on f.course_type_id = ct.id "
				+ "inner join course c with(nolock) on c.faculty_id = f.id inner join institute_course ic with(nolock) on ic.course_id = c.id "
				+ "where ic.country_id = :countryId")
				.setParameter("countryId", countryID);
		List<Object[]> rows = query.list();
		List<CourseType> courseTypes = new ArrayList<>();
		for(Object[] row : rows){
			CourseType obj = new CourseType();
			obj.setId(Integer.parseInt(row[0].toString()));
			obj.setName(row[1].toString());
			courseTypes.add(obj);
		}
		return courseTypes;
	}
 
	
}
