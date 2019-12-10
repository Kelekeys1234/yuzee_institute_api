package com.seeka.app.dao;

import java.math.BigInteger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteWorldRankingHistory;

@Repository
public class InstituteWorldRankingHistoryDAO implements IInstituteWorldRankingHistoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final InstituteWorldRankingHistory worldRanking) {
		Session session = sessionFactory.getCurrentSession();
		session.save(worldRanking);
	}

	@Override
	public InstituteWorldRankingHistory getHistoryOfWorldRanking(final BigInteger instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InstituteWorldRankingHistory.class, "instituteWorldRankingHistory");
		criteria.createAlias("InstituteWorldRankingHistory.institute", "institute");
		criteria.add(Restrictions.eq("institute.id", instituteId));
		return (InstituteWorldRankingHistory) criteria.uniqueResult();
	}

}
