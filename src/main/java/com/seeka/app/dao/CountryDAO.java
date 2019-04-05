package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Country;
import com.seeka.app.dto.CountryDto;

@Repository
public class CountryDAO implements ICountryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Country obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public List<Country> getAll() {
		System.out.println("Inside Country DAO....");
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Country> list = session.createCriteria(Country.class).list();
		return list;
	}

	@Override
	public Country get(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Country country = session.get(Country.class, id);
		return country;
	}

	@Override
	public List<CountryDto> getAllUniversityCountries() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select distinct c.id, c.name as name, c.country_code as code from country c with(nolock) "
						+ "inner join institute i with(nolock) on i.country_id = c.id");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<CountryDto>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(Integer.parseInt(row[0].toString()));
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
				"SELECT c.id,c.name as name,c.country_code as code FROM country c with(nolock) WHERE c.name LIKE '%" + name + "%'");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<CountryDto>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(Integer.parseInt(row[0].toString()));
			obj.setName(row[1].toString());
			obj.setCountryCode(row[2].toString());
			countries.add(obj);
		}
		return countries;
	}
	
	@Override
	public List<CountryDto> getAllCountries() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"SELECT c.id,c.name as name,c.country_code as code FROM country c with(nolock)");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<CountryDto>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(Integer.parseInt(row[0].toString()));
			obj.setName(row[1].toString());
			obj.setCountryCode(row[2].toString());
			countries.add(obj);
		}
		return countries;
	}

}
