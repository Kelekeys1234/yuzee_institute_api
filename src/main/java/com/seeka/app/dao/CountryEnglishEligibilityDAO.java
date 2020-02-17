package com.seeka.app.dao;import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CountryEnglishEligibility;

@Repository
public class CountryEnglishEligibilityDAO implements ICountryEnglishEligibilityDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(CountryEnglishEligibility obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public List<CountryEnglishEligibility> getAll() {
		System.out.println("Inside CountryEnglishEligibility DAO....");
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<CountryEnglishEligibility> list = session.createCriteria(CountryEnglishEligibility.class).list();
		return list;
	}

	@Override
	public CountryEnglishEligibility get(String id) {
		System.out.println("String : "+id);
		Session session = sessionFactory.getCurrentSession();		
		CountryEnglishEligibility CountryEnglishEligibility = session.get(CountryEnglishEligibility.class, id);
		return CountryEnglishEligibility;
	}

	@Override
	public List<CountryEnglishEligibility> getEnglishEligibiltyList(String countryId) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(CountryEnglishEligibility.class);
		crit.add(Restrictions.eq("countryId",countryId));
		return crit.list();
	}
	
/*
	@Override
	public List<CountryDto> searchInterestByCountry(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"SELECT c.id,c.name as name,c.country_code as code FROM CountryEnglishEligibility c with WHERE c.name LIKE '%" + name + "%'");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<CountryDto>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(BigInteger.fromString((row[0].toString())));
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
				"SELECT c.id,c.name as name,c.country_code as code FROM CountryEnglishEligibility c with");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<CountryDto>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(BigInteger.fromString((row[0].toString())));
			obj.setName(row[1].toString());
			obj.setCountryCode(row[2].toString());
			countries.add(obj);
		}
		return countries;
	}*/

}
