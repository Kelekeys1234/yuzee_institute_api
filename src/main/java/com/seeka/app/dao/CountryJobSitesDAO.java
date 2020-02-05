package com.seeka.app.dao;import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CountryJobSites;
import com.seeka.app.dto.CountryDto;

@Repository
public class CountryJobSitesDAO implements ICountryJobSitesDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(CountryJobSites obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public List<CountryJobSites> getAll() {
		System.out.println("Inside CountryJobSites DAO....");
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<CountryJobSites> list = session.createCriteria(CountryJobSites.class).list();
		return list;
	}

	@Override
	public CountryJobSites get(BigInteger id) {
		System.out.println("BigInteger : "+id);
		//Session session = sessionFactory.getCurrentSession();
		Session session = sessionFactory.getCurrentSession();		
		 
		
		//CountryJobSites CountryJobSites = session.load(CountryJobSites.class, id);
		
		CountryJobSites CountryJobSites = session.get(CountryJobSites.class, id);
		return CountryJobSites;
	}

	@Override
	public List<CountryDto> getAllUniversityCountries() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select distinct c.id, c.name as name, c.country_code as code from CountryJobSites c  "
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
				"SELECT c.id,c.name as name,c.country_code as code FROM CountryJobSites c  WHERE c.name LIKE '%" + name + "%'");
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
		Query query = session.createSQLQuery("SELECT c.id,c.name as name,c.country_code as code FROM CountryJobSites c ");
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
