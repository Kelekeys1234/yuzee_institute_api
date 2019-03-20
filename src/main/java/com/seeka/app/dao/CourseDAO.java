package com.seeka.app.dao;

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
	    try{
		    String sqlQuery="From Course c where 1=1 ";
		    Map<String, Object> paramMap = new HashMap<String, Object>();
		    
		    if(null != filterObj.getCountryId()) {
		    	sqlQuery = sqlQuery + " and c.countryObj.id = :countryIDParam";
		    	paramMap.put("countryIDParam", filterObj.getCountryId());
		    }
		    
		    if(null != filterObj.getFacultyId()) {
		    	sqlQuery = sqlQuery + " and c.facultyObj.id = :facultyIDParam";
		    	paramMap.put("facultyIDParam", filterObj.getFacultyId());
		    }
		    
		    if(null != filterObj.getLevelId()) {
		    	sqlQuery = sqlQuery + " and c.facultyObj.levelObj.id = :levelIDParam";
		    	paramMap.put("levelIDParam", filterObj.getLevelId());
		    }
		    
		    if(null != filterObj.getSearchKey() && !filterObj.getSearchKey().isEmpty()) {
		    	sqlQuery = sqlQuery + " and c.name like :searchKeyParam ";
		    	paramMap.put("searchKeyParam", "%"+filterObj.getSearchKey()+"%");
		    } 
		    Session session=sessionFactory.getCurrentSession();
		    Query query=session.createQuery(sqlQuery).setProperties(paramMap);
		    List<Course> list=query.list();
		    list.stream().forEach((p)->{System.out.println(p.getName());});    
		    return list;
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	    return null;
	}
	
	 
}
