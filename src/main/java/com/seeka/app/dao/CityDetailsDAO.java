package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.City;
import com.seeka.app.bean.CityDetails;

@Repository
public class CityDetailsDAO implements ICityDetailsDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<CityDetails> getAll() {		
		Session session = sessionFactory.getCurrentSession();		
		List<CityDetails> list = session.createCriteria(CityDetails.class).list();	   					
		return list;
	}
	
	@Override
	public CityDetails get(BigInteger id) {	
		Session session = sessionFactory.getCurrentSession();		
		CityDetails cityDetails = session.get(CityDetails.class, id);
		return cityDetails;
	}
	
	/*@Override
	public List<City> getCountry() {
		Session session = sessionFactory.getCurrentSession();	
		String hql = "SELECT * FROM City INNER JOIN FROM Country ON City.countryId=Country.id";				 
		Query query = (Query) session.createQuery(hql);
		List<City> result = query.getResultList();
		return  result;
	}*/
	
	public List<CityDetails> getAllCitiesByCountry(BigInteger countryId) {		
		Session session = sessionFactory.getCurrentSession();				 
		Criteria criteria = session.createCriteria(City.class);	   	
		criteria.add(Restrictions.eq("countryObj.id", countryId));
		return criteria.list();
	}
}
