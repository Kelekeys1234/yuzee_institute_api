package com.seeka.app.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;
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
	public List<UserWatchCourse> getUserWatchCourse(final BigInteger userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserWatchCourse.class, "userWatchCourse");
		crit.add(Restrictions.eq("userId", userId));
		crit.addOrder(Order.desc("userWatchCourse.updatedOn"));
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
	public List<UserWatchArticle> getUserWatchArticle(final BigInteger userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(UserWatchArticle.class, "userWatchArticle");
		crit.add(Restrictions.eq("userId", userId));
		return crit.list();
	}

	@Override
	public List<Course> getRecommendCourse(final BigInteger facultyId, final BigInteger instituteId, final BigInteger countryId, final BigInteger cityId,
			final BigDecimal price, final BigDecimal variablePrice, final int pageSize, final List<BigInteger> courseIds) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Course.class, "course");
		crit.createAlias("course.faculty", "faculty");

		crit.createAlias("course.institute", "institute");
		crit.createAlias("course.country", "country");
		crit.createAlias("course.city", "city");
		crit.add(Restrictions.eq("faculty.id", facultyId));
		if (instituteId != null) {
			crit.add(Restrictions.eq("institute.id", instituteId));
		}
		crit.add(Restrictions.eq("country.id", countryId));
		if (cityId != null) {
			crit.add(Restrictions.eq("city.id", cityId));
		}
		if (price != null) {
			BigDecimal low = price.subtract(variablePrice);
			if (low.compareTo(BigDecimal.ZERO) < 0) {
				low = BigDecimal.ZERO;
			}
			BigDecimal high = price.add(variablePrice);
			crit.add(Restrictions.between("course.internationalFee", low, high));
			crit.addOrder(Order.asc("course.internationalFee"));
		}
		if (courseIds != null && !courseIds.isEmpty()) {
			crit.add(Restrictions.not(Restrictions.in("course.id", courseIds.toArray())));
		}

		crit.setFirstResult(0);
		crit.setMaxResults(pageSize);

		return crit.list();
	}

}
