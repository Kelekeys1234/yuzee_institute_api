package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseGradeEligibility;

@Repository
public class CourseGradeEligibilityDAO implements ICourseGradeEligibilityDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final CourseGradeEligibility obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public void update(final CourseGradeEligibility obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
	}

	@Override
	public CourseGradeEligibility get(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		CourseGradeEligibility obj = session.get(CourseGradeEligibility.class, id);
		return obj;
	}

	@Override
	public List<CourseGradeEligibility> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class);
		return crit.list();
	}

	@SuppressWarnings("deprecation")
	@Override
	public CourseGradeEligibility getAllEnglishEligibilityByCourse(final String courseID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(CourseGradeEligibility.class);
		crit.add(Restrictions.eq("course.id", courseID));

		List<CourseGradeEligibility> result = crit.list();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

}
