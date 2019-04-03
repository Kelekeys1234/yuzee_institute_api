package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.Faculty;
import com.seeka.app.dto.CourseSearchDto;

@Repository
public class CourseDAO implements ICourseDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(Course obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(Course obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public Course get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		Course obj = session.get(Course.class, id);
		return obj;
	}
	
	@Override
	public List<Course> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Course.class); 
		return crit.list();
	} 

	@Override
	public List<Course> getAllCoursesByFilter(CourseSearchDto filterObj) {
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createSQLQuery(
				"select distinct cp.cost_range, cp.currency,inst.id as instId,inst.name as instName,city.id as cityId,city.name as cityName,ctry.id as countryId,ctry.name as countryName,crs.id,crs.name,crs.duration,crs.duration_time,crs.world_ranking,crs.course_lang from course crs with(nolock) "
				+ "inner join course_pricing cp with(nolock) on cp.course_id = crs.id inner join institute inst with(nolock) on crs.institute_id = inst.id "
				+ "inner join country ctry with(nolock) on ctry.id = crs.country_id inner join city city with(nolock) on city.id = crs.city_id where crs.country_id = 345 ");					    
		    List<Object[]> rows = query.list();
			List<Course> list = new ArrayList<Course>();
			Course obj = null;			
			for(Object[] row : rows){
				obj = new Course();				
				obj.setId(Integer.parseInt(row[8].toString()));
				obj.setName(row[9].toString());	
				obj.setDuration(row[10].toString());
				obj.setDurationTime(row[11].toString());
				obj.setWorldRanking(row[12].toString());
				obj.setCourseLanguage(row[13].toString());
				
				list.add(obj);
			}    
		    return list;	   
	}
	
	 
}
