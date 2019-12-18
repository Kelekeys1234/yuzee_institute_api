package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserSearch;

@Repository
public class UserSearchDaoImpl implements UserSearchDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void createUserSearchEntry(final UserSearch userSearch) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userSearch);
	}

	@Override
	public void deleteUserSearchEntry(final BigInteger userId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createNativeQuery("delete from user_search where user_id = ? and entity_type = ?");
		query.setParameter(1, userId);
		query.setParameter(2, entityType);
		query.executeUpdate();
	}

	@Override
	public List<UserSearch> getUserSearchEntry(final BigInteger userId, final String entityType, final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserSearch.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("entityType", entityType));
		criteria.addOrder(Order.desc("createdOn"));
		criteria.setFetchSize(pageSize);
		criteria.setFirstResult(startIndex);
		return criteria.list();
	}

	@Override
	public Integer getUserSearchEntryCount(final BigInteger userId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserSearch.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("entityType", entityType));
		criteria.setProjection(Projections.property("id"));
		List<UserSearch> userSearchList = criteria.list();
		return userSearchList == null ? 0 : userSearchList.size();

	}

	@Override
	public List<String> getUserSearchKeyword(final Integer startIndex, final Integer pageSize, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserSearch.class);
		criteria.setProjection(Projections.property("searchString"));
		if (searchKeyword != null && !searchKeyword.isEmpty()) {
			criteria.add(Restrictions.ilike("searchString", searchKeyword, MatchMode.ANYWHERE));
		}
		if (startIndex != null && pageSize != null) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}
		return criteria.list();
	}

}
