package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.FaqCategory;

@SuppressWarnings({ "unchecked", "deprecation" })
@Repository
public class FaqCategoryDao implements IFaqCategoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveFaqCategory(final FaqCategory faqCategory) {
		Session session = sessionFactory.getCurrentSession();
		session.save(faqCategory);
	}

	@Override
	public void updateFaqCategory(final FaqCategory faqCategory) {
		Session session = sessionFactory.getCurrentSession();
		session.update(faqCategory);

	}

	@Override
	public FaqCategory getFaqCategoryDetail(final BigInteger faqCategoryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(FaqCategory.class, "faqCategory");
		criteria.add(Restrictions.eq("faqCategory.id", faqCategoryId));
		return (FaqCategory) criteria.uniqueResult();
	}

	@Override
	public List<FaqCategory> getFaqCategoryList(final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(FaqCategory.class, "faqCategory");
		criteria.add(Restrictions.eq("faqCategory.isActive", true));
		if (startIndex != null && pageSize != null) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}

		return criteria.list();
	}

	@Override
	public int getFaqCategoryCount() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(FaqCategory.class, "faqCategory");
		criteria.add(Restrictions.eq("faqCategory.isActive", true));
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}

	@Override
	public FaqCategory getFaqCategoryBasedOnName(final String name, final BigInteger faqCategoryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(FaqCategory.class, "faqCategory");
		criteria.add(Restrictions.eq("faqCategory.name", name).ignoreCase());
		if (faqCategoryId != null) {
			criteria.add(Restrictions.not(Restrictions.eq("faqCategory.id", faqCategoryId)));
		}
		criteria.add(Restrictions.eq("faqCategory.isActive", true));
		return (FaqCategory) criteria.uniqueResult();
	}

}
