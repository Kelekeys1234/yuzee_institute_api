package com.seeka.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteVideos;

@SuppressWarnings({ "deprecation", "unchecked" })
@Repository
public class InstituteVideoDao implements IInstituteVideoDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final InstituteVideos instituteVideo) {
		Session session = sessionFactory.getCurrentSession();
		session.save(instituteVideo);
	}

	@Override
	public List<InstituteVideos> findByInstituteId(final String id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(InstituteVideos.class);
		crit.add(Restrictions.eq("institute.id", id));
		return crit.list();
	}
}
