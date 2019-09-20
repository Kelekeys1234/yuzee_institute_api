package com.seeka.app.dao;

import java.math.BigInteger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.EnrollmentChatMedia;

@Repository
public class EnrollmentChatMediaDao implements IEnrollmentChatMediaDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final EnrollmentChatMedia enrollmentChatImages) {
		Session session = sessionFactory.getCurrentSession();
		session.save(enrollmentChatImages);
	}

	@Override
	public EnrollmentChatMedia getEnrollmentImage(final BigInteger enrollmentChatConversationId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EnrollmentChatMedia.class, "enrollmentMedia");
		criteria.createAlias("enrollmentMedia.enrollmentChatConversation", "enrollmentChatConversation");
		criteria.add(Restrictions.eq("enrollmentChatConversation.id", enrollmentChatConversationId));
		return (EnrollmentChatMedia) criteria.uniqueResult();
	}

}
