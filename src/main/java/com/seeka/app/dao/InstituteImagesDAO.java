package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteImages;

@Repository
public class InstituteImagesDAO implements IInstituteImagesDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final InstituteImages obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public void update(final InstituteImages obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
	}

	@Override
	public InstituteImages get(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		InstituteImages obj = session.get(InstituteImages.class, id);
		return obj;
	}

	@Override
	public List<InstituteImages> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(InstituteImages.class);
		return crit.list();
	}

	@Override
	public List<InstituteImages> getInstituteImageListBasedOnId(final BigInteger instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(InstituteImages.class, "instituteImages");
		crit.createAlias("instituteImages.institute", "institute");
		crit.add(Restrictions.eq("institute.id", instituteId));
		crit.add(Restrictions.eq("instituteImages.isActive", true));
		return crit.list();
	}

}
