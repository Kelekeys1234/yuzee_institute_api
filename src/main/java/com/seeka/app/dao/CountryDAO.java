package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Country;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.DiscoverCountryDto;

@Repository
@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
public class CountryDAO implements ICountryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Country save(final Country obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
		return obj;
	}

	@Override
	public List<Country> getAll() {
		System.out.println("Inside Country DAO....");
		Session session = sessionFactory.getCurrentSession();
		List<Country> list = session.createCriteria(Country.class).list();
		return list;
	}

	@Override
	public Country get(final String id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Country.class, id);
	}

	@Override
	public List<CountryDto> getAllUniversityCountries() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select distinct c.id, c.name as name, c.country_code as code from country c  " + "inner join institute i  on i.country_id = c.id");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			if (row[2] != null) {
				obj.setCountryCode(row[2].toString());
			}
			countries.add(obj);
		}
		return countries;

	}

	@Override
	public List<Country> getAllCountryByIds(final List<BigInteger> countryIds) {
		Session session = sessionFactory.getCurrentSession();
		List<Country> countries = session.createQuery("SELECT c FROM country c WHERE c.id IN :ids").setParameter("ids", countryIds).getResultList();
		return countries;

	}

	@Override
	public List<CountryDto> searchInterestByCountry(final String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("SELECT c.id,c.name as name,c.country_code as code FROM country c  WHERE c.name LIKE '%" + name + "%'");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<>();
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
		Query query = session.createSQLQuery("SELECT c.id, c.name as name, c.country_code as code FROM country c ORDER BY c.name");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			if (row[2] != null) {
				obj.setCountryCode(row[2].toString());
			}
			countries.add(obj);
		}
		return countries;
	}

	@Override
	public List<CountryDto> getAllCountryName() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select distinct c.id, c.name as name, c.country_code as code from country c  "
				+ "inner join institute i  on i.country_id = c.id ORDER BY c.name");
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			if (row[2] != null) {
				obj.setCountryCode(row[2].toString());
			}
			countries.add(obj);
		}
		return countries;
	}

	@Override
	public List<DiscoverCountryDto> getDiscoverCountry() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"SELECT c.id, c.name as name, c.country_code as code, ci.image_path FROM country c left join country_images ci on c.id = ci.country_id ORDER BY c.name");
		List<Object[]> rows = query.list();
		List<DiscoverCountryDto> countries = new ArrayList<>();
		DiscoverCountryDto obj = null;
		for (Object[] row : rows) {
			obj = new DiscoverCountryDto();
			obj.setId(new BigInteger(row[0].toString()));
			obj.setName(row[1].toString());
			if (row[2] != null) {
				obj.setCountryCode(row[2].toString());
			}
			if (row[3] != null) {
				obj.setImageUrl(row[3].toString());
			}
			countries.add(obj);
		}
		return countries;
	}

	@Override
	public List<CountryDto> autoSearch(final int pageNumber, final int pageSize, final String searchKey) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select distinct c.id, c.name as name, c.country_code as code from country c where c.name like '%" + searchKey.trim() + "%'";
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CountryDto> countries = new ArrayList<>();
		CountryDto obj = null;
		for (Object[] row : rows) {
			obj = new CountryDto();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			if (row[2] != null) {
				obj.setCountryCode(row[2].toString());
			}
			countries.add(obj);
		}
		return countries;
	}

	@Override
	public Country getCountryBasedOnCitizenship(final String citizenship) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Country.class, "country");
		System.out.println("citizenship-- " + citizenship);
		crit.add(Restrictions.eq("name", citizenship));
		return (Country) (crit.list().size() > 0 ? crit.list().get(0) : crit.uniqueResult());
	}

	@Override
	public List<Country> getCountryIdsBasedOnCitizenships(final List<String> citizenships) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Country.class, "country");
		System.out.println("citizenship-- " + citizenships);
		crit.add(Restrictions.in("name", citizenships));
		return crit.list().size() > 0 ? crit.list() : new ArrayList();
	}

	@Override
	public Country getCountryByName(final String countryName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Country.class, "country");
		if (countryName != null) {
			criteria.add(Restrictions.eq("country.name", countryName));
		}
		return (Country) criteria.uniqueResult();
	}
}
