package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserWatchArticle;
import com.seeka.app.bean.UserWatchCourse;

@Repository
public class UserRecommendationDaoImpl implements UserRecommendationDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final UserWatchCourse userWatchCourse) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userWatchCourse);
	}

	@Override
	public UserWatchCourse getUserWatchCourseByUserIdAndCourseId(final BigInteger userId, final BigInteger courseId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserWatchCourse.class, "userWatchCourse");
		crit.createAlias("userWatchCourse.course", "course");
		crit.add(Restrictions.eq("course.id", courseId));
		crit.add(Restrictions.eq("userId", userId));
		return (UserWatchCourse) crit.uniqueResult();
	}

	@Override
	public List<UserWatchCourse> getRecommendCourse(final BigInteger userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserWatchCourse.class, "userWatchCourse");
		crit.add(Restrictions.eq("userId", userId));
		return crit.list();
	}

	@Override
	public void save(final UserWatchArticle userWatchArticle) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userWatchArticle);
	}

	@Override
	public UserWatchArticle getUserWatchArticleByUserIdAndArticleId(final BigInteger userId, final BigInteger articleId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserWatchArticle.class, "userWatchArticle");
		crit.createAlias("userWatchArticle.seekaArticles", "seekaArticles");
		crit.add(Restrictions.eq("seekaArticles.id", articleId));
		crit.add(Restrictions.eq("userId", userId));
		return (UserWatchArticle) crit.uniqueResult();
	}

	@Override
	public List<UserWatchArticle> getRecommendArticle(final BigInteger userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserWatchArticle.class, "userWatchArticle");
		crit.add(Restrictions.eq("userId", userId));
		return crit.list();
	}

}
