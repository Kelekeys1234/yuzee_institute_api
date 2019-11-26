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
	public List<Object> getUserViewData(final BigInteger userId, final String entityType, final boolean isUnique, final Integer startIndex,
			final Integer pageSize) {
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
		if (startIndex != null && pageSize != null) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
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

	@Override
	public List<Object> getUserViewDataBasedOnEntityIdList(final BigInteger userId, final String entityType, final boolean isUnique,
			final List<BigInteger> entityIds) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserViewData.class, "userViewData");
		crit.add(Restrictions.and(Restrictions.eq("userViewData.userId", userId)));
		crit.add(Restrictions.and(Restrictions.eq("userViewData.entityType", entityType)));
		crit.add(Restrictions.in("userViewData.entityId", entityIds));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("userViewData.entityId"), "entityId");
		if (isUnique) {
			projList.add(Projections.groupProperty("userViewData.entityId"));
			projList.add(Projections.groupProperty("userViewData.entityType"));
		}
		crit.setProjection(projList);

		return crit.list();
	}

	@Override
	public List<BigInteger> getUserWatchCourseIds(final BigInteger userId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
//		Criteria crit = session.createCriteria(UserViewData.class, "userViewData");
//		crit.add(Restrictions.and(Restrictions.eq("userViewData.userId", userId)));
//		crit.add(Restrictions.and(Restrictions.eq("userViewData.entityType", entityType)));
//		ProjectionList projList = Projections.projectionList();
//		projList.add(Projections.property("userViewData.entityId"), "entityId");
//		projList.add(Projections.groupProperty("userViewData.entityId"), "entityId");
//		projList.add(Projections.count("userViewData.entityId"), "count");
//		crit.addOrder(Order.desc("count"));
//		crit.setProjection(projList);
		List<BigInteger> courseIds = session
				.createNativeQuery("Select entity_id from user_view_data where user_id = ? and entity_type = ? group by entity_id order by count(*) desc")
				.setParameter(1, userId).setParameter(2, entityType).getResultList();
		return courseIds;
	}

	@Override
	public List<BigInteger> getOtherUserWatchCourse(final BigInteger userId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		List<BigInteger> courseList = session.createNativeQuery(
				"select entity_id from user_view_data userwatchcourse where userwatchcourse.user_Id not in (?) and userwatchcourse.entity_type = ? group by userwatchcourse.entity_id order by count(*) desc")
				.setParameter(1, userId).getResultList();
		return courseList;
	}
}
