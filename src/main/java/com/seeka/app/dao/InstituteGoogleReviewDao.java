package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteGoogleReview;

@Repository("iinstituteGoogleReviewDao")
public class InstituteGoogleReviewDao implements IInstituteGoogleReviewDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final InstituteGoogleReview instituteGoogleReview) {
		Session session = sessionFactory.getCurrentSession();
		session.save(instituteGoogleReview);
	}

	@Override
	public void update(final InstituteGoogleReview instituteGoogleReview) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(instituteGoogleReview);
		tx.commit();
		session.close();
	}

	@Override
	public InstituteGoogleReview getInstituteGoogleReviewDetail(final BigInteger instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InstituteGoogleReview.class, "instituteGoogleReview");
		criteria.add(Restrictions.eq("instituteGoogleReview.instituteId", instituteId));
		return (InstituteGoogleReview) criteria.uniqueResult();
	}

	@Override
	public List<InstituteGoogleReview> getInstituteGoogleReview(final int startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InstituteGoogleReview.class, "instituteGoogleReview");
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(pageSize);

		return criteria.list();
	}

	@Override
	public int getCountOfGooglereview() {
		Session session = sessionFactory.getCurrentSession();
		return ((Long) session.createQuery("select count(*) from InstituteGoogleReview").uniqueResult()).intValue();
	}

}
