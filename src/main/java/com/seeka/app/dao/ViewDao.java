package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
import com.seeka.app.dto.UserCourseView;

@SuppressWarnings("unchecked")
@Repository
public class ViewDao implements IViewDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void createUserViewData(final UserViewData userViewData) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userViewData);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Object> getUserViewData(final String userId, final String entityType, final boolean isUnique, final Integer startIndex,
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
	public int getUserViewDataCountBasedOnUserId(final String userId, final String entityId, final String entityType) {
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
	public List<Object> getUserViewDataBasedOnEntityIdList(final String userId, final String entityType, final boolean isUnique,
			final List<String> entityIds) {
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
	public List<String> getUserWatchCourseIds(final String userId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		return session.createNativeQuery("Select entity_id from user_view_data where user_id = ? and entity_type = ? group by entity_id order by count(*) desc")
				.setParameter(1, userId).setParameter(2, entityType).getResultList();
	}

	@Override
	public List<String> getOtherUserWatchCourse(final String userId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		return session.createNativeQuery(
				"select entity_id from user_view_data userwatchcourse where userwatchcourse.user_Id not in (?) and userwatchcourse.entity_type = ? group by userwatchcourse.entity_id order by count(*) desc")
				.setParameter(1, userId).getResultList();
	}

	@Override
	public List<UserCourseView> userVisistedCourseBasedOncity(final String cityId, final Date fromDate, final Date toDate) {
		Session session = sessionFactory.getCurrentSession();
		List<Object[]> rows = session.createNativeQuery(
				"select count(*),abc.course_id from (select count(*) as count ,course.id  as course_id from user_view_data join course on course.id=user_view_data.entity_id "
						+ " where entity_type='COURSE' and course.city_id=? and Date(user_view_data.created_on) between ? and ? group by entity_id,user_id) as abc group by abc.course_id ")
				.setParameter(1, cityId).setParameter(2, fromDate).setParameter(3, toDate).getResultList();
		List<UserCourseView> result = new ArrayList<>();
		for (Object[] row : rows) {
			UserCourseView userCourseView = new UserCourseView();
			userCourseView.setCount(Integer.parseInt(String.valueOf(row[0])));
			userCourseView.setCourseId(new BigInteger(String.valueOf(row[1])));
			result.add(userCourseView);
		}
		return result;
	}

	@Override
	public List<UserCourseView> userVisistedCourseBasedOnCountry(final String countryId, final Date fromDate, final Date toDate) {
		Session session = sessionFactory.getCurrentSession();
		List<Object[]> rows = session.createNativeQuery(
				"select count(*),abc.course_id from (select count(*) as count ,course.id  as course_id from user_view_data join course on course.id=user_view_data.entity_id "
						+ " where entity_type='COURSE' and course.country_id=? and Date(user_view_data.created_on) between ? and ? group by entity_id,user_id) as abc group by abc.course_id ")
				.setParameter(1, countryId).setParameter(2, fromDate).setParameter(3, toDate).getResultList();
		List<UserCourseView> result = new ArrayList<>();
		for (Object[] row : rows) {
			UserCourseView userCourseView = new UserCourseView();
			userCourseView.setCount(Integer.parseInt(String.valueOf(row[0])));
			userCourseView.setCourseId(new BigInteger(String.valueOf(row[1])));
			result.add(userCourseView);
		}
		return result;
	}
}
