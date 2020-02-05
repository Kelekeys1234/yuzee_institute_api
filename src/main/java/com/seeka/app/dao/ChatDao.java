package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Chat;

@Repository
public class ChatDao implements IChatDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final Chat chat) {
		Session session = sessionFactory.getCurrentSession();
		session.save(chat);
	}

	@Override
	public void update(final Chat chat) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(chat);
		tx.commit();
		session.close();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Chat> getChatList(final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Chat.class, "chat");
		if (startIndex != null && pageSize != null) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		crit.addOrder(Order.desc("chat.createdOn"));
		return crit.list();
	}

	@Override
	public Chat getChat(final String chatId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Chat.class, chatId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Chat getChatBasedOnEntityId(final BigInteger entityId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Chat.class, "chat");
		crit.add(Restrictions.eq("chat.id", entityId));
		return (Chat) crit.uniqueResult();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Chat getChatBasedOnEntityIdAndEntityType(final BigInteger entityId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Chat.class, "chat");
		crit.add(Restrictions.eq("chat.entityId", entityId));
		crit.add(Restrictions.eq("chat.entityType", entityType));
		return (Chat) crit.uniqueResult();
	}

}
