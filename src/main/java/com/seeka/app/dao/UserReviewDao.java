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
	public List<UserReview> getUserReview(final BigInteger userId, final BigInteger entityId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserReview.class, "userReview");
		if (entityId != null && entityType != null) {
			crit.add(Restrictions.eq("userReview.entityId", entityId));
			crit.add(Restrictions.eq("userReview.entityType", entityType));
		}

		if (userId != null) {
			crit.add(Restrictions.eq("userId", userId));
		}
		return crit.list();

	}

	@Override
	public UserReview getUserAverageReview(final BigInteger entityId, final String entityType) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserReview.class, "userReview");
		if (entityId != null && entityType != null) {
			crit.add(Restrictions.eq("userReview.entityId", entityId));
			crit.add(Restrictions.eq("userReview.entityType", entityType));
		}

		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.avg("overallUniversityRating"), "overallUniversityRating");
		projList.add(Projections.avg("jobProspects"), "jobProspects");
		projList.add(Projections.avg("courseAndLectures"), "courseAndLectures");
		projList.add(Projections.avg("studentUnion"), "studentUnion");
		projList.add(Projections.avg("institutionFacilities"), "institutionFacilities");
		projList.add(Projections.avg("cityLife"), "cityLife");
		projList.add(Projections.avg("clubsAndSocieties"), "clubsAndSocieties");
		projList.add(Projections.avg("studentSupport"), "studentSupport");
		projList.add(Projections.property("entityId"), "entityId");
		projList.add(Projections.property("entityType"), "entityType");

		crit.setProjection(projList);
		Object[] obj = (Object[]) crit.uniqueResult();
		UserReview userReview = new UserReview();
		userReview.setOverallUniversityRating((Double) obj[0]);
		userReview.setJobProspects((Double) obj[1]);
		userReview.setCourseAndLectures((Double) obj[2]);
		userReview.setStudentUnion((Double) obj[3]);
		userReview.setInstitutionFacilities((Double) obj[4]);
		userReview.setCityLife((Double) obj[5]);
		userReview.setClubsAndSocieties((Double) obj[6]);
		userReview.setStudentSupport((Double) obj[7]);
		userReview.setEntityId(entityId);
		userReview.setEntityType(entityType);
		return userReview;
	}

}
