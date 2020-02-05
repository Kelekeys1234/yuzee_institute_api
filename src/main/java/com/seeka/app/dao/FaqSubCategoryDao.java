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

import com.seeka.app.bean.FaqSubCategory;

@SuppressWarnings({ "unchecked", "deprecation" })
@Repository
public class FaqSubCategoryDao implements IFaqSubCategoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveFaqSubCategory(final FaqSubCategory faqSubCategory) {
		Session session = sessionFactory.getCurrentSession();
		session.save(faqSubCategory);
	}

	@Override
	public void updateFaqSubCategory(final FaqSubCategory faqSubCategory) {
		Session session = sessionFactory.getCurrentSession();
		session.update(faqSubCategory);

	}

	@Override
	public FaqSubCategory getFaqSubCategoryDetail(final String faqSubCategoryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(FaqSubCategory.class, "faqSubCategory");
		criteria.add(Restrictions.eq("faqSubCategory.id", faqSubCategoryId));
		return (FaqSubCategory) criteria.uniqueResult();
	}

	@Override
	public List<FaqSubCategory> getFaqSubCategoryList(final Integer startIndex, final Integer pageSize, final String faqCategoryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(FaqSubCategory.class, "faqSubCategory");
		criteria.createAlias("faqSubCategory.faqCategory", "faqCategory");
		criteria.add(Restrictions.eq("faqSubCategory.isActive", true));
		if (faqCategoryId != null) {
			criteria.add(Restrictions.eq("faqCategory.id", faqCategoryId));
		}
		if (startIndex != null && pageSize != null) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}

		return criteria.list();
	}

	@Override
	public int getFaqSubCategoryCount(final String faqCategoryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(FaqSubCategory.class, "faqSubCategory");
		criteria.createAlias("faqSubCategory.faqCategory", "faqCategory");
		criteria.add(Restrictions.eq("faqSubCategory.isActive", true));
		if (faqCategoryId != null) {
			criteria.add(Restrictions.eq("faqCategory.id", faqCategoryId));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}

	@Override
	public FaqSubCategory getFaqSubCategoryBasedOnName(final String name, final String faqCategoryId, final String faqSubCategoryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(FaqSubCategory.class, "faqSubCategory");
		criteria.createAlias("faqSubCategory.faqCategory", "faqCategory");
		criteria.add(Restrictions.eq("faqSubCategory.name", name).ignoreCase());
		criteria.add(Restrictions.eq("faqCategory.id", faqCategoryId));
		if (faqSubCategoryId != null) {
			criteria.add(Restrictions.not(Restrictions.eq("faqSubCategory.id", faqSubCategoryId)));
		}
		criteria.add(Restrictions.eq("faqSubCategory.isActive", true));
		return (FaqSubCategory) criteria.uniqueResult();
	}

}
