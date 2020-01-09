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

import com.seeka.app.bean.EnrollmentChat;

@Repository
public class EnrollmentChatDao implements IEnrollmentChatDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final EnrollmentChat enrollmentChat) {
		Session session = sessionFactory.getCurrentSession();
		session.save(enrollmentChat);
	}

	@Override
	public void update(final EnrollmentChat enrollmentChat) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(enrollmentChat);
		tx.commit();
		session.close();
	}

	@Override
	public List<EnrollmentChat> getEnrollmentChatList(final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(EnrollmentChat.class, "enrollmentChat");
		if (startIndex != null && pageSize != null) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		return crit.list();
	}

	@Override
	public EnrollmentChat getEnrollmentChat(final BigInteger enrollmentChatId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(EnrollmentChat.class, enrollmentChatId);
	}

	@Override
	public EnrollmentChat getEnrollmentChatBasedOnEnrollmentId(final BigInteger enrollmentId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(EnrollmentChat.class, "enrollmentChat");
		crit.createAlias("enrollmentChat.enrollment", "enrollment");
		crit.add(Restrictions.eq("enrollment.id", enrollmentId));
		return (EnrollmentChat) crit.uniqueResult();
	}

}
