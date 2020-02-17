package com.seeka.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CourseMinRequirement;

@Repository
public class CourseMinRequirementDao implements ICourseMinRequirementDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final CourseMinRequirement obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public List<CourseMinRequirement> get(final String course) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(CourseMinRequirement.class, "courseMinRequirement");
//		crit.createAlias("courseMinRequirement.country", "country");
		crit.createAlias("courseMinRequirement.course", "course");
		crit.add(Restrictions.eq("course.id", course));
//		crit.setProjection(Projections.groupProperty("country.id"));
		return crit.list();
	}

}
