package com.seeka.app.dao;import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Hobbies;


@Repository
public class HobbyDAO implements IHobbyDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Hobbies hobbiesObj) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(hobbiesObj);	   					
	}
	
	@Override
	public void update(Hobbies hobbiesObj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(hobbiesObj);	   					
	}
	
	@Override
	public Hobbies get(BigInteger id) {	
		Session session = sessionFactory.getCurrentSession();		
		Hobbies obj = session.get(Hobbies.class, id);
		return obj;
	}
	
	@Override
	public List<Hobbies> searchByHobbies(String hobbyTxt){
		Session session = sessionFactory.getCurrentSession();		
		String sqlQuery = "SELECT h.id,h.hobby_txt FROM hobbies h WHERE 1=1 ";
		if(null != hobbyTxt && !hobbyTxt.isEmpty()) {
			sqlQuery = "and h.hobby_txt LIKE '%"+hobbyTxt+"%'";
		}
		
		Query query = session.createSQLQuery(sqlQuery);		
		List<Object[]> rows = query.list();
		List<Hobbies> hobbies = new ArrayList<Hobbies>();
		Hobbies obj = null;
		for(Object[] row : rows){
			obj = new Hobbies();
			obj.setId(new BigInteger(row[0].toString()));	
			obj.setHobbyTxt(row[1].toString());	
			hobbies.add(obj);
		}
		return hobbies;
	}
	
	
}
