package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Currency;

@Repository
public class CurrencyDAO implements ICurrencyDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(Currency obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(Currency obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public Currency get(UUID id) {	
		Session session = sessionFactory.getCurrentSession();		
		Currency obj = session.get(Currency.class, id);
		return obj;
	}
	
	@Override
	public List<Currency> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Currency.class);
		return crit.list();
	}
	
	@Override
	public List<Currency> getCourseTypeByCountryId(UUID countryID) {
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createSQLQuery(
				"select distinct ct.id, ct.type_txt as courseType from course_type ct with(nolock) inner join faculty f with(nolock) on f.course_type_id = ct.id "
				+ "inner join course c with(nolock) on c.faculty_id = f.id inner join institute_course ic with(nolock) on ic.course_id = c.id "
				+ "where ic.country_id = :countryId")
				.setParameter("countryId", countryID);
		List<Object[]> rows = query.list();
		List<Currency> courseTypes = new ArrayList<>();
		for(Object[] row : rows){
			Currency obj = new Currency();
			obj.setId(UUID.fromString((row[0].toString())));
			obj.setName(row[1].toString());
			courseTypes.add(obj);
		}
		return courseTypes;
	}
 
	@Override
	public List<Currency> getCurrencyByCountryId(UUID countryId) {
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createSQLQuery(
				"select distinct le.id, le.name as name,le.Currency_key as Currencykey from Currency le with(nolock) inner join institute_Currency il with(nolock) on il.Currency_id = le.id "
				+ "inner join country c with(nolock) on c.id = il.country_id "
				+ "where il.country_id = :countryId")
				.setParameter("countryId", countryId);
				
		List<Object[]> rows = query.list();
		
		List<Currency> Currency = new ArrayList<>();
		
		for(Object[] row : rows){
			Currency obj = new Currency();
			obj.setId(UUID.fromString((row[0].toString())));
			obj.setName(row[1].toString());
			//obj.setCurrencyKey(row[2].toString());
			Currency.add(obj);
		}
		return Currency;
	}
	
}
