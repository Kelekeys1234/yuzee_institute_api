package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.City;

@Repository
@SuppressWarnings("unchecked")
public class CityDAO implements ICityDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final City obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public List<City> getAll() {
		List<City> citis = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select c.id, c.name as name from city c  ORDER BY c.name");
		List<Object[]> rows = query.list();
		City obj = null;
		for (Object[] row : rows) {
			obj = new City();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			citis.add(obj);
		}
		return citis;
	}

	@Override
	public City get(final String id) {
		Session session = sessionFactory.getCurrentSession();
		City city = session.get(City.class, id);
		return city;
	}

	@Override
	public List<City> getAllCitiesByCountry(final String countryId) {
		List<City> citis = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select c.id, c.name as name from city c  where c.country_id = " + countryId + " ORDER BY c.name");
		List<Object[]> rows = query.list();
		City obj = null;
		for (Object[] row : rows) {
			obj = new City();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			citis.add(obj);
		}
		return citis;
	}

	@Override
	public List<City> getAllMultipleCitiesByCountry(final String BigIntegers) {
		List<City> citis = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select c.id, c.name as name from city c  where c.country_id in (" + BigIntegers + ") ORDER BY c.name");
		List<Object[]> rows = query.list();
		City obj = null;
		for (Object[] row : rows) {
			obj = new City();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			citis.add(obj);
		}
		City allObj = new City();
		allObj.setId("111111");
		allObj.setName("All");
		citis.add(allObj);
		return citis;
	}

	@Override
	public List<City> getAllCityByIds(final List<BigInteger> cityIds) {
		Session session = sessionFactory.getCurrentSession();
		List<City> cities = session.createQuery("SELECT c FROM city c WHERE c.id IN :ids").setParameter("ids", cityIds).getResultList();
		return cities;
	}

	@Override
	public List<City> getAllCityNames(final Integer startIndex, final Integer pageSize, final String searchString) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(City.class, "city");
		if (searchString != null) {
			criteria.add(Restrictions.ilike("city.name", searchString, MatchMode.ANYWHERE));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(pageSize);
		// criteria.setProjection(Projections.property("city.name"));
		return criteria.list();
	}

	@Override
	public Integer getAllCityNamesCount(final String searchString) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(City.class, "city");
		if (searchString != null) {
			criteria.add(Restrictions.ilike("city.name", searchString, MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.property("city.name"));
		List<String> cityNameList = criteria.list();
		Set<String> cityNameSet = cityNameList == null ? null : new HashSet<>(cityNameList);
		return cityNameSet != null ? cityNameSet.size() : 0;
	}

	@Override
	public City getCityByName(final String cityName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(City.class, "city");
		if (cityName != null) {
			criteria.add(Restrictions.eq("city.name", cityName));
		}
		return (City) criteria.uniqueResult();
	}
}
