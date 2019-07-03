package com.seeka.app.dao;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteType;

@Repository
public class InstituteTypeDAO implements IInstituteTypeDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(InstituteType instituteType) {
		Session session = sessionFactory.getCurrentSession();
		session.save(instituteType);
	}

	@Override
	public void update(InstituteType instituteType) {
		Session session = sessionFactory.getCurrentSession();
		session.update(instituteType);
	}

	@Override
	public InstituteType get(BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(InstituteType.class, id);
	}

}
