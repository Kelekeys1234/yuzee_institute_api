package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteLevel;

@Repository
public class InstituteLevelDAO implements IInstituteLevelDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final InstituteLevel obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public void update(final InstituteLevel obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
	}

	@Override
	public InstituteLevel get(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		InstituteLevel obj = session.get(InstituteLevel.class, id);
		return obj;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<InstituteLevel> getAllLevelByInstituteId(final String instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(InstituteLevel.class, "instituteLevel");
		crit.createAlias("instituteLevel.institute", "institute");
		crit.add(Restrictions.eq("institute.id", instituteId));
		return crit.list();
	}

	@Override
	public void deleteInstituteLevel(final String instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("DELETE FROM institute_level WHERE institute_id =" + instituteId);
		query.executeUpdate();

	}

}
