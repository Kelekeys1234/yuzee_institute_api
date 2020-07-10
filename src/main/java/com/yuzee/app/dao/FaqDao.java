package com.yuzee.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Faq;

@Repository
public class FaqDao implements IFaqDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final Faq faq) {
		Session session = sessionFactory.getCurrentSession();
		session.save(faq);
	}

	@Override
	public void update(final Faq faq) {
		Session session = sessionFactory.getCurrentSession();
		session.update(faq);
	}

	@Override
	public Faq getFaqDetail(final String faqId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Faq.class, faqId);
	}

	@Override
	public List<Faq> getFaqList(final Integer startIndex, final Integer pageSize, final String faqCategoryId, final String faqSubCategoryId,
			final String sortByField, String sortByType, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Faq.class, "faq");
		criteria.createAlias("faq.faqCategory", "faqCategory");
		criteria.createAlias("faq.faqSubCategory", "faqSubCategory");
		criteria.add(Restrictions.eq("faq.isActive", true));
		if (startIndex != null && pageSize != null) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}
		if (faqCategoryId != null) {
			criteria.add(Restrictions.eq("faqCategory.id", faqCategoryId));
		}
		if (faqSubCategoryId != null) {
			criteria.add(Restrictions.eq("faqSubCategory.id", faqSubCategoryId));
		}
		if (searchKeyword != null) {
			criteria.add(Restrictions.disjunction().add(Restrictions.ilike("faq.title", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("faq.description", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("faqCategory.name", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("faqSubCategory.name", searchKeyword, MatchMode.ANYWHERE)));
		}
		if (sortByType == null) {
			sortByType = "DESC";
		}
		if (sortByField != null) {
			if ("title".equals(sortByField)) {
				if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc("faq.title"));
				} else if ("DESC".equals(sortByType)) {
					criteria.addOrder(Order.desc("faq.title"));
				}
			} else if ("description".equals(sortByField)) {
				if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc("faq.description"));
				} else if ("DESC".equals(sortByType)) {
					criteria.addOrder(Order.desc("faq.description"));
				}
			} else if ("faqCategory".equals(sortByField)) {
				if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc("faqCategory.name"));
				} else if ("DESC".equals(sortByType)) {
					criteria.addOrder(Order.desc("faqCategory.name"));
				}
			} else if ("faqSubCategory".equals(sortByField)) {
				if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc("faqSubCategory.name"));
				} else if ("DESC".equals(sortByType)) {
					criteria.addOrder(Order.desc("faqSubCategory.name"));
				}
			}
		} else {
			if ("ASC".equals(sortByType)) {
				criteria.addOrder(Order.asc("faq.id"));
			} else if ("DESC".equals(sortByType)) {
				criteria.addOrder(Order.desc("faq.id"));
			}
		}
		return criteria.list();
	}

	@Override
	public int getFaqCount(final String faqCategoryId, final String faqSubCategoryId, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Faq.class, "faq");
		criteria.createAlias("faq.faqCategory", "faqCategory");
		criteria.createAlias("faq.faqSubCategory", "faqSubCategory");
		criteria.add(Restrictions.eq("faq.isActive", true));
		if (faqCategoryId != null) {
			criteria.add(Restrictions.eq("faqCategory.id", faqCategoryId));
		}
		if (faqSubCategoryId != null) {
			criteria.add(Restrictions.eq("faqSubCategory.id", faqSubCategoryId));
		}
		if (searchKeyword != null) {
			criteria.add(Restrictions.disjunction().add(Restrictions.ilike("faq.title", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("faq.description", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("faqCategory.name", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("faqSubCategory.name", searchKeyword, MatchMode.ANYWHERE)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}

	@Override
	public List<Faq> getFaqBasedOnEntityId(String entityId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Faq.class, "faq");
		criteria.add(Restrictions.eq("entityId",entityId));
		criteria.add(Restrictions.eq("isActive",true));
		return criteria.list();
	}
	
	

}
