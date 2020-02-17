package com.seeka.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserMyCourse;

/**
 *
 * @author SeekADegree
 *
 */
@Repository
public class UserMyCourseDAO implements IUserMyCourseDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final UserMyCourse reviewObj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(reviewObj);
	}

	@Override
	public void update(final UserMyCourse reviewObj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(reviewObj);
	}

	@Override
	public UserMyCourse get(final String userId) {
		Session session = sessionFactory.getCurrentSession();
		UserMyCourse user = session.get(UserMyCourse.class, userId);
		return user;
	}

	@Override
	public UserMyCourse getDataByUserIDAndCourseID(final String userId, final String courseId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserMyCourse.class, "userMyCourse");
		crit.createAlias("userMyCourse.course", "course");
		crit.add(Restrictions.eq("course.id", courseId));
		crit.add(Restrictions.eq("userId", userId));
		return (UserMyCourse) crit.uniqueResult();
	}

	@Override
	public List<UserMyCourse> getDataByUserID(final String userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserMyCourse.class, "userMyCourse");
		crit.add(Restrictions.eq("userId", userId));
		crit.add(Restrictions.eq("isActive", true));
		return crit.list();
	}

	@Override
	public List<String> getAllCourseIdsByUser(final String userId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select course_id from user_my_course where is_active = 1 and user_id ='" + userId + "'";
		Query query = session.createSQLQuery(sqlQuery);
		List<String> rows = query.list();
		return rows;
	}

}
