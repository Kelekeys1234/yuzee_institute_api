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
	public List<UserReview> getUserReviewList(final BigInteger userId, final BigInteger entityId, final String entityType, final Integer startIndex,
			final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserReview.class, "userReview");
		if (entityId != null && entityType != null) {
			crit.add(Restrictions.eq("userReview.entityId", entityId));
			crit.add(Restrictions.eq("userReview.entityType", entityType));
		}

		if (userId != null) {
			crit.add(Restrictions.eq("userId", userId));
		}
		if (startIndex != null && pageSize != null) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		return crit.list();

	}

	@Override
	public Double getReviewStar(final BigInteger entityId, final String entityType) {
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
	public List<Object> getUserAverageReview(final BigInteger entityId, final String entityType) {
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
	public UserReview getUserReview(final BigInteger userReviewId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserReview.class, "userReview");
		crit.add(Restrictions.eq("id", userReviewId));
		return (UserReview) crit.uniqueResult();
	}

	@Override
	public List<UserReviewRating> getUserReviewRatings(final BigInteger userReviewId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserReviewRating.class, "userReviewRating");
		crit.createAlias("userReviewRating.userReview", "userReview");
		crit.add(Restrictions.eq("userReview.id", userReviewId));
		return crit.list();
	}

}
