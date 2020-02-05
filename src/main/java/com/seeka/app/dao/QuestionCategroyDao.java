package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.QuestionCategroy;

@Repository
public class QuestionCategroyDao implements IQuestionCategroyDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addQuestionCategory(final QuestionCategroy questionCategroy) {
		Session session = sessionFactory.getCurrentSession();
		session.save(questionCategroy);
	}

	@Override
	public void update(final QuestionCategroy questionCategroy) {
		Session session = sessionFactory.getCurrentSession();
		session.update(questionCategroy);
	}

	@Override
	public List<QuestionCategroy> getQuestionCategoryList() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(QuestionCategroy.class, "questionCategory");
		criteria.add(Restrictions.and(Restrictions.eq("questionCategory.isActive", true)));
		return criteria.list();
	}

	@Override
	public QuestionCategroy getQuestionCategory(final String questionCategoryId, final Boolean isActive) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(QuestionCategroy.class, "questionCategory");
		if (isActive != null) {
			criteria.add(Restrictions.and(Restrictions.eq("questionCategory.isActive", isActive)));
		}
		criteria.add(Restrictions.and(Restrictions.eq("questionCategory.id", questionCategoryId)));
		return (QuestionCategroy) criteria.uniqueResult();
	}

	@Override
	public QuestionCategroy getQuestionCategoryName(final String categoryName, final Boolean isActive) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(QuestionCategroy.class, "questionCategory");
		if (isActive != null) {
			criteria.add(Restrictions.and(Restrictions.eq("questionCategory.isActive", isActive)));
		}
		criteria.add(Restrictions.and(Restrictions.eq("questionCategory.categoryName", categoryName)));
		return (QuestionCategroy) criteria.uniqueResult();
	}

}
