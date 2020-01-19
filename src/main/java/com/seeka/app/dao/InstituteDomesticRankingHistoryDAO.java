package com.seeka.app.dao;

import java.math.BigInteger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteDomesticRankingHistory;

@Repository
public class InstituteDomesticRankingHistoryDAO implements IInstituteDomesticRankingHistoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final InstituteDomesticRankingHistory domesticRanking) {
		Session session = sessionFactory.getCurrentSession();
		session.save(domesticRanking);
	}

	@Override
	public InstituteDomesticRankingHistory getHistoryOfDomesticRanking(final BigInteger instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InstituteDomesticRankingHistory.class, "instituteDomesticRankingHistory");
		criteria.createAlias("instituteDomesticRankingHistory.institute", "institute");
		criteria.add(Restrictions.eq("institute.id", instituteId));
		return (InstituteDomesticRankingHistory) criteria.uniqueResult();
	}

}
