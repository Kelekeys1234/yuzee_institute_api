package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserReview;
import com.seeka.app.bean.UserReviewRating;

/**
 *
 * @author SeekADegree
 *
 */
@Repository
public class UserReviewDao implements IUserReviewDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final UserReview userReview) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userReview);
	}

	@Override
	public void saveUserReviewRating(final UserReviewRating userReviewRating) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userReviewRating);
	}

	@Override
	public List<UserReview> getUserReviewList(final String userId, final String entityId, final String entityType, final Integer startIndex,
			final Integer pageSize, final String sortByType, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserReview.class, "userReview");
		if (entityId != null && entityType != null) {
			crit.add(Restrictions.eq("userReview.entityId", entityId));
			crit.add(Restrictions.eq("userReview.entityType", entityType));
		}

		if (userId != null) {
			crit.add(Restrictions.eq("userId", userId));
		}

		if (searchKeyword != null) {
			crit.add(Restrictions.ilike("userReview.comments", searchKeyword, MatchMode.ANYWHERE));
		}
		if (sortByType != null && "ASC".equals(sortByType)) {
			crit.addOrder(Order.asc("userReview.id"));
		} else {
			crit.addOrder(Order.desc("userReview.id"));
		}

		if (startIndex != null && pageSize != null) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		return crit.list();

	}

	@Override
	public Double getReviewStar(final String entityId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserReview.class, "userReview");
		if (entityId != null && entityType != null) {
			crit.add(Restrictions.eq("userReview.entityId", entityId));
			crit.add(Restrictions.eq("userReview.entityType", entityType));
		}
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.avg("userReview.reviewStar"), "reviewStar");
		crit.setProjection(projList);
		return (Double) crit.uniqueResult();
	}

	@Override
	public List<Object> getUserAverageReview(final String entityId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserReviewRating.class, "userReviewRating");
		crit.createAlias("userReviewRating.userReview", "userReview");
		if (entityId != null && entityType != null) {
			crit.add(Restrictions.eq("userReview.entityId", entityId));
			crit.add(Restrictions.eq("userReview.entityType", entityType));
		}

		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("userReviewRating.reviewQuestionId"));
		projList.add(Projections.avg("userReviewRating.rating"), "rating");
		projList.add(Projections.property("userReview.entityId"), "entityId");
		projList.add(Projections.property("userReview.entityType"), "entityType");

		crit.setProjection(projList);
		return crit.list();
	}

	@Override
	public UserReview getUserReview(final String userReviewId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserReview.class, "userReview");
		crit.add(Restrictions.eq("id", userReviewId));
		return (UserReview) crit.uniqueResult();
	}

	@Override
	@Transactional
	public List<UserReviewRating> getUserReviewRatings(final String userReviewId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserReviewRating.class, "userReviewRating");
		crit.createAlias("userReviewRating.userReview", "userReview");
		crit.add(Restrictions.eq("userReview.id", userReviewId));
		return crit.list();
	}

	@Override
	public int getUserReviewCount(final String userId, final String entityId, final String entityType, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserReview.class, "userReview");
		if (entityId != null && entityType != null) {
			crit.add(Restrictions.eq("userReview.entityId", entityId));
			crit.add(Restrictions.eq("userReview.entityType", entityType));
		}

		if (searchKeyword != null) {
			crit.add(Restrictions.ilike("userReview.comments", searchKeyword, MatchMode.ANYWHERE));
		}

		if (userId != null) {
			crit.add(Restrictions.eq("userId", userId));
		}
		crit.setProjection(Projections.rowCount());
		return ((Long) crit.uniqueResult()).intValue();
	}

	@Override
	public Map<String, Double> getUserAverageReviewList(final List<String> entityIdList, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserReview.class, "userReview");
		if (entityIdList != null && !entityIdList.isEmpty() && entityType != null) {
			criteria.add(Restrictions.in("userReview.entityId", entityIdList));
			criteria.add(Restrictions.eq("userReview.entityType", entityType));
		}

		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("userReview.entityId"));
		projList.add(Projections.groupProperty("userReview.entityType"));
		projList.add(Projections.property("userReview.entityId"), "entityId");
		projList.add(Projections.avg("userReview.reviewStar"), "reviewStar");
		criteria.setProjection(projList);
		List<Object> objectList = criteria.list();
		Map<String, Double> resultMap = new HashMap<>();
		for (Object object : objectList) {
			Object[] obj1 = (Object[]) object;
			resultMap.put((String) obj1[0], (Double) obj1[3]);
		}
		return resultMap;

	}

}
