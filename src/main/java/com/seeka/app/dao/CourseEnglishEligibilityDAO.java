package com.seeka.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseEnglishEligibility;

@Repository
@SuppressWarnings({ "deprecation", "unchecked" })
public class CourseEnglishEligibilityDAO implements ICourseEnglishEligibilityDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final CourseEnglishEligibility obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public void update(final CourseEnglishEligibility obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
	}

	@Override	
	public CourseEnglishEligibility get(final String id) {
		Session session = sessionFactory.getCurrentSession();
		CourseEnglishEligibility obj = session.get(CourseEnglishEligibility.class, id);
		return obj;
	}

	@Override
	public List<CourseEnglishEligibility> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class);
		return crit.list();
	}

    @Override
	public List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(final String courseID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(CourseEnglishEligibility.class, "courseEnglishEligibility");
		c.createAlias("courseEnglishEligibility.course", "course");
		c.add(Restrictions.eq("course.id", courseID));
		c.add(Restrictions.eq("isActive", true));
		return c.list();
	}
	
	public void delete(final String courseID){
	    Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(CourseEnglishEligibility.class, "courseEnglishEligibility");
        c.createAlias("courseEnglishEligibility.course", "course");
        c.add(Restrictions.eq("course.id", courseID));
	}

}
