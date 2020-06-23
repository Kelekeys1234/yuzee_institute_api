package com.seeka.app.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteGoogleReview;
import com.seeka.app.dao.InstituteGoogleReviewDao;

@Repository("iinstituteGoogleReviewDao")
public class InstituteGoogleReviewDaoImpl implements InstituteGoogleReviewDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final InstituteGoogleReview instituteGoogleReview) {
		Session session = sessionFactory.getCurrentSession();
		session.save(instituteGoogleReview);
	}

	@Override
	public void update(final InstituteGoogleReview instituteGoogleReview) {
		Session session = sessionFactory.getCurrentSession();
		session.update(instituteGoogleReview);
	}

	@Override
	public List<InstituteGoogleReview> getInstituteGoogleReview(final String instituteId, final int startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InstituteGoogleReview.class, "instituteGoogleReview");
		criteria.createAlias("instituteGoogleReview.institute", "institute");
		criteria.add(Restrictions.eq("institute.id", instituteId));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(pageSize);

		return criteria.list();
	}

	@Override
	public int getCountOfGooglereview(final String instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InstituteGoogleReview.class, "instituteGoogleReview");
		criteria.createAlias("instituteGoogleReview.institute", "institute");
		criteria.add(Restrictions.eq("institute.id", instituteId));
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}

	@Override
	public Double getInstituteAvgGoogleReview(final String instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InstituteGoogleReview.class, "instituteGoogleReview");
		criteria.createAlias("instituteGoogleReview.institute", "institute");
		criteria.add(Restrictions.eq("institute.id", instituteId));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.avg("instituteGoogleReview.reviewStar"), "reviewStar");
		criteria.setProjection(projList);
		return (Double) criteria.uniqueResult();
	}

	@Override
	public Map<String, Double> getInstituteAvgGoogleReviewForList(final List<String> instituteIdList) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InstituteGoogleReview.class, "instituteGoogleReview");
		criteria.createAlias("instituteGoogleReview.institute", "institute");
		criteria.add(Restrictions.in("institute.id", instituteIdList));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("institute.id"));
		projList.add(Projections.property("institute.id"), "instituteId");
		projList.add(Projections.avg("instituteGoogleReview.reviewStar"), "reviewStar");
		criteria.setProjection(projList);
		List<Object> objectList = criteria.list();
		Map<String, Double> resultMap = new HashMap<>();
		for (Object object : objectList) {
			Object[] obj1 = (Object[]) object;
			resultMap.put((String) obj1[1], (Double) obj1[2]);
		}
		return resultMap;

	}

}
