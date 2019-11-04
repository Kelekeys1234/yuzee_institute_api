package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserViewData;

@Repository
public class ViewDao implements IViewDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void createUserViewData(final UserViewData userViewData) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userViewData);
	}

	@Override
	public List<Object> getUserViewData(final BigInteger userId, final String entityType, final boolean isUnique) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserViewData.class, "userViewData");
		crit.add(Restrictions.and(Restrictions.eq("userViewData.userId", userId)));
		crit.add(Restrictions.and(Restrictions.eq("userViewData.entityType", entityType)));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("userViewData.id"), "id");
		projList.add(Projections.property("userViewData.userId"), "userId");
		projList.add(Projections.property("userViewData.entityId"), "entityId");
		projList.add(Projections.property("userViewData.entityType"), "entityType");
		projList.add(Projections.property("userViewData.createdOn"), "createdOn");
		projList.add(Projections.property("userViewData.createdBy"), "createdBy");
		if (isUnique) {
			projList.add(Projections.groupProperty("userViewData.entityId"));
			projList.add(Projections.groupProperty("userViewData.entityType"));
		}
		crit.setProjection(projList);

		return crit.list();
	}

	@Override
	public int getUserViewDataCountBasedOnUserId(final BigInteger userId, final BigInteger entityId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserViewData.class, "userViewData");
		crit.add(Restrictions.and(Restrictions.eq("userViewData.userId", userId)));
		crit.add(Restrictions.and(Restrictions.eq("userViewData.entityId", entityId)));
		crit.add(Restrictions.and(Restrictions.eq("userViewData.entityType", entityType)));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.rowCount());
		crit.setProjection(projList);
		return ((Long) crit.uniqueResult()).intValue();
	}

	@Override
	public int getUserViewDataCountBasedOnEntityId(final BigInteger entityId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserViewData.class, "userViewData");
		crit.add(Restrictions.and(Restrictions.eq("userViewData.entityId", entityId)));
		crit.add(Restrictions.and(Restrictions.eq("userViewData.entityType", entityType)));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.rowCount());
		crit.setProjection(projList);
		return ((Long) crit.uniqueResult()).intValue();
	}

}
