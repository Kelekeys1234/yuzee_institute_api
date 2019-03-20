package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.Level;

@Repository
public class FacultyDAO implements IFacultyDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(Faculty obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(Faculty obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public Faculty get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		Faculty obj = session.get(Faculty.class, id);
		return obj;
	}
	
	@Override
	public List<Faculty> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Institute.class);
		return crit.list();
	} 
	
	@Override
	public List<Faculty> getFacultyByCountryIdAndCourseTypeId(Integer countryID,Integer courseTypeId) {
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createSQLQuery(
				"select distinct f.id, f.name as facultyName from faculty f with(nolock) "
				+ "inner join course c with(nolock) on c.faculty_id = f.id inner join institute_course ic with(nolock) on ic.course_id = c.id "
				+ "where ic.country_id = :countryId and f.course_type_id = :courseTypeId")
				.setParameter("countryId", countryID).setParameter("courseTypeId", courseTypeId);
		List<Object[]> rows = query.list();
		List<Faculty> faculties = new ArrayList<Faculty>();
		Faculty obj = null;
		for(Object[] row : rows){
			obj = new Faculty();
			obj.setId(Integer.parseInt(row[0].toString()));
			obj.setName(row[1].toString());
			faculties.add(obj);
		}
		return faculties;
	}
	
		public static void main(String[] args) {
	   
	    Faculty obj = new Faculty();
	    Level levlObj = new Level();
	    levlObj.setId(1);
	    
	    obj.setLevelObj(levlObj);
		obj.setName("Engineering");
		obj.setDescription("description");
		obj.setIsActive(true);
		obj.setCreatedOn(new Date());
		obj.setUpdatedOn(new Date());
		obj.setDeletedOn(new Date());
		obj.setCreatedBy("Own");
		obj.setUpdatedBy("Own");
		obj.setIsDeleted(true);					
		Gson gson = new Gson();
		
		String value = gson.toJson(obj);
		
		 System.out.println(value);
		 
		System.out.println(new Date().getTime());
	}
}
