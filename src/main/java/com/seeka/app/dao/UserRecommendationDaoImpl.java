package com.seeka.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;

@Repository
public class UserRecommendationDaoImpl implements UserRecommendationDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Course> getRecommendCourse(final String facultyId, final String instituteId, final String countryId, final String cityId,
			final Double price, final Double variablePrice, final int pageSize, final List<String> courseIds) {
		return getRelatedCourse(facultyId, instituteId, countryId, cityId, price, variablePrice, pageSize, courseIds, null);
	}

	@Override
	public List<Course> getRelatedCourse(final String facultyId, final String instituteId, final String countryId, final String cityId,
			final Double price, final Double variablePrice, final int pageSize, final List<String> courseIds, final String courseName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class, "course");
		crit.createAlias("course.faculty", "faculty");

		crit.createAlias("course.institute", "institute");
		crit.add(Restrictions.eq("faculty.id", facultyId));
		if (instituteId != null) {
			crit.add(Restrictions.eq("institute.id", instituteId));
		}

		if (price != null) {
			Double low = price - variablePrice;
			if (low < 0) {
				low = Double.valueOf(0);
			}
			Double high = price + variablePrice;
			crit.add(Restrictions.between("course.usdInternationFee", low, high));
			crit.addOrder(Order.asc("course.usdInternationFee"));
		}
		if (courseIds != null && !courseIds.isEmpty()) {
			crit.add(Restrictions.not(Restrictions.in("course.id", courseIds.toArray())));
		}

		if (courseName != null) {
			crit.add(Restrictions.eq("course.name", courseName));
		}

		crit.setFirstResult(0);
		crit.setMaxResults(pageSize);

		return crit.list();
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Course> getCourseNoResultRecommendation(final String facultyId, final String countryId, final List<String> courseIds,
			final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class, "course");
		crit.createAlias("course.faculty", "faculty");
		if (facultyId != null) {
			crit.add(Restrictions.eq("faculty.id", facultyId));
		}

		if (courseIds != null && !courseIds.isEmpty()) {
			crit.add(Restrictions.not(Restrictions.in("course.id", courseIds.toArray())));
		}
		if (startIndex != null && pageSize != null) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		return crit.list();
	}

	@Override
	public List<Course> getCheapestCourse(final String facultyId, final String countryId, final String levelId, final String cityId,
			final List<String> courseIds, final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class, "course");
		crit.createAlias("course.faculty", "faculty");
		crit.createAlias("course.level", "level");
		if (facultyId != null) {
			crit.add(Restrictions.eq("faculty.id", facultyId));
		}
		if (levelId != null) {
			crit.add(Restrictions.eq("level.id", levelId));
		}
		if (courseIds != null && !courseIds.isEmpty()) {
			crit.add(Restrictions.not(Restrictions.in("course.id", courseIds.toArray())));
		}
		crit.addOrder(Order.asc("course.usdInternationFee"));

		if (startIndex != null && pageSize != null) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		return crit.list();
	}

}
