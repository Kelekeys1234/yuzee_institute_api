package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ChatConversation;

@Repository
public class ChatConversationDao implements IChatConversationDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final ChatConversation chatConversation) {
		Session session = sessionFactory.getCurrentSession();
		session.save(chatConversation);
	}

	@Override
	public void update(final ChatConversation chatConversation) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(chatConversation);
		tx.commit();
		session.close();
	}

	@Override
	public ChatConversation getChatConversation(final BigInteger chatConversationId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(ChatConversation.class, chatConversationId);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<ChatConversation> getChatConversationBasedOnChatId(final BigInteger chatId, final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(ChatConversation.class, "chatConversation");
		crit.createAlias("chatConversation.chat", "chat");
		crit.add(Restrictions.eq("chat.id", chatId));
		if (startIndex != null && pageSize != null) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		crit.addOrder(Order.desc("chatConversation.createdOn"));
		return crit.list();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer getChatConversationCountBasedOnChatId(final BigInteger chatId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(ChatConversation.class, "chatConversation");
		crit.createAlias("chatConversation.chat", "chat");
		crit.add(Restrictions.eq("chat.id", chatId));
		crit.setProjection(Projections.rowCount());
		return ((Long) crit.uniqueResult()).intValue();
	}

	@Override
	public List<ChatConversation> getChatListBasedOnEntityType(final String entityType, final BigInteger initiateFromId, final BigInteger initiateToId,
			final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(ChatConversation.class, "chatConversation");
		crit.createAlias("chatConversation.chat", "chat");
		crit.add(Restrictions.eq("chat.entityType", entityType));
		crit.add(Restrictions.disjunction().add(Restrictions.eq("chatConversation.initiateFromId", initiateFromId))
				.add(Restrictions.eq("chatConversation.initiateFromId", initiateToId)));
		crit.add(Restrictions.disjunction().add(Restrictions.eq("chatConversation.initiateToId", initiateFromId))
				.add(Restrictions.eq("chatConversation.initiateToId", initiateToId)));
		if (startIndex != null && pageSize != null) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		return crit.list();
	}

	@Override
	public Integer getChatConversationCountBasedOnEntityType(final String entityType, final BigInteger initiateFromId, final BigInteger initiateToId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(ChatConversation.class, "chatConversation");
		crit.createAlias("chatConversation.chat", "chat");
		crit.add(Restrictions.disjunction().add(Restrictions.eq("chatConversation.initiateFromId", initiateFromId))
				.add(Restrictions.eq("chatConversation.initiateFromId", initiateToId)));
		crit.add(Restrictions.disjunction().add(Restrictions.eq("chatConversation.initiateToId", initiateFromId))
				.add(Restrictions.eq("chatConversation.initiateToId", initiateToId)));
		crit.add(Restrictions.eq("chat.entityType", entityType));
		crit.setProjection(Projections.rowCount());
		return ((Long) crit.uniqueResult()).intValue();
	}

}
