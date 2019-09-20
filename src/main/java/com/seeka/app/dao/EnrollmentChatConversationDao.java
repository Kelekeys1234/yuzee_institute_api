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

import com.seeka.app.bean.EnrollmentChatConversation;

@Repository
public class EnrollmentChatConversationDao implements IEnrollmentChatConversationDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final EnrollmentChatConversation enrollmentChatConversation) {
		Session session = sessionFactory.getCurrentSession();
		session.save(enrollmentChatConversation);
	}

	@Override
	public void update(final EnrollmentChatConversation enrollmentChatConversation) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(enrollmentChatConversation);
		tx.commit();
		session.close();
	}

	@Override
	public EnrollmentChatConversation getEnrollmentChatConversation(final BigInteger enrollmentChatConversationId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(EnrollmentChatConversation.class, enrollmentChatConversationId);
	}

	@Override
	public List<EnrollmentChatConversation> getEnrollmentChatConversationBasedOnEnrollmentChatId(final BigInteger enrollmentChatId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(EnrollmentChatConversation.class, "enrollmentChatConversation");
		crit.createAlias("enrollmentChatConversation.enrollmentChat", "enrollmentChat");
		crit.add(Restrictions.eq("enrollmentChat.id", enrollmentChatId));
		return crit.list();
	}

}
