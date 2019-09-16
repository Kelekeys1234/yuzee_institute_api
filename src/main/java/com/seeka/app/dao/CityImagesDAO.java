package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CityImages;

@Repository
public class CityImagesDAO implements ICityImagesDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final CityImages obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public void update(final CityImages obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
	}

	@Override
	public CityImages get(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		CityImages obj = session.get(CityImages.class, id);
		return obj;
	}

	@Override
	public List<CityImages> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(CityImages.class);
		return crit.list();
	}

	@Override
	public List<CityImages> getCityImageListBasedOnCityId(final BigInteger cityId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(CityImages.class, "cityImages");
		crit.createAlias("cityImages.city", "city");
		crit.add(Restrictions.eq("city.id", cityId));
		crit.add(Restrictions.eq("cityImages.isActive", true));
		return crit.list();
	}

}
