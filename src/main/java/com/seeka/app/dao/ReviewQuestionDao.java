package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ReviewQuestions;

@Repository
public class ReviewQuestionDao implements IReviewQuestionDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addReviewQuestions(final ReviewQuestions reviewQuestions) {
		Session session = sessionFactory.getCurrentSession();
		session.save(reviewQuestions);
	}

	@Override
	public void updateReviewQuestions(final ReviewQuestions reviewQuestions) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(reviewQuestions);
		tx.commit();
		session.close();
	}

	@Override
	public ReviewQuestions getReviewQuestion(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(ReviewQuestions.class, id);
	}

	@Override
	public List<ReviewQuestions> getReviewQuestionList() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(ReviewQuestions.class);
		return crit.list();
	}

	@Override
	public List<ReviewQuestions> getReviewQuestionListBasedOnParam(final Boolean isActive, final String studentType, final String studentCategory,
			final BigInteger questionCategoryId, final BigInteger questionId, final BigInteger notQuestionId) {

		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(ReviewQuestions.class, "reviewQuestions");
		if (isActive != null) {
			crit.add(Restrictions.eq("reviewQuestions.isActive", isActive));
		}
		if (studentType != null) {
			crit.add(Restrictions.eq("reviewQuestions.studentType", studentType));
		}
		if (studentCategory != null) {
			crit.add(Restrictions.eq("reviewQuestions.studentCategory", studentCategory));
		}
		if (questionCategoryId != null) {
			crit.add(Restrictions.eq("reviewQuestions.questionCategoryId", questionCategoryId));
		}
		if (questionId != null) {
			crit.add(Restrictions.eq("reviewQuestions.id", questionId));
		}
		if (notQuestionId != null) {
			crit.add(Restrictions.not(Restrictions.eq("reviewQuestions.id", notQuestionId)));
		}
		return crit.list();
	}

}
