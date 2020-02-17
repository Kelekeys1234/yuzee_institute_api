package com.seeka.app.dao;import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CountryEmployers;
import com.seeka.app.dto.CountryDto;

@Repository
public class CountryEmployerDAO implements ICountryEmployerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(CountryEmployers obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public List<CountryEmployers> getAll() {
		System.out.println("Inside CountryEmployers DAO....");
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<CountryEmployers> list = session.createCriteria(CountryEmployers.class).list();
		return list;
	}

	@Override
	public CountryEmployers get(String id) {
		System.out.println("String : "+id);
		//Session session = sessionFactory.getCurrentSession();
		Session session = sessionFactory.getCurrentSession();		
		 
		
		//CountryEmployers CountryEmployers = session.load(CountryEmployers.class, id);
		
		CountryEmployers CountryEmployers = session.get(CountryEmployers.class, id);
		return CountryEmployers;
	}

	@Override
	public List<CountryDto> getAllUniversityCountries() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select distinct c.id, c.name as name, c.country_code as code from CountryEmployers c  "
						+ "inner join institute i  on i.country_id = c.id");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<CountryDto>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			obj.setCountryCode(row[2].toString());
			countries.add(obj);
		}
		return countries;

	}

	@Override
	public List<CountryDto> searchInterestByCountry(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"SELECT c.id,c.name as name,c.country_code as code FROM CountryEmployers c  WHERE c.name LIKE '%" + name + "%'");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<CountryDto>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			obj.setCountryCode(row[2].toString());
			countries.add(obj);
		}
		return countries;
	}
	
	@Override
	public List<CountryDto> getAllCountries() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("SELECT c.id,c.name as name,c.country_code as code FROM CountryEmployers c ");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<CountryDto>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			obj.setCountryCode(row[2].toString());
			countries.add(obj);
		}
		return countries;
	}

}
