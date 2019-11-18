package com.seeka.app.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.SeekaArticles;

@Repository
@SuppressWarnings({ "unchecked", "deprecation"})
public class ArticleDAO implements IArticleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public Integer getTotalSearchCount(String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SeekaArticles.class, "seeka_article");
		if (searchKeyword != null) {
			criteria.add(Restrictions.ilike("seeka_article.heading", searchKeyword, MatchMode.ANYWHERE));
		}
		List<Object[]> rows = criteria.list();
		return rows.size();
	}
	
	@Override
	public SeekaArticles save(final SeekaArticles article) {
		Session session = sessionFactory.getCurrentSession();
		session.save(article);
		return article;
	}	
	
	@Override
	public SeekaArticles findById(final BigInteger uId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(SeekaArticles.class, uId);
	}

	@Override
	public SeekaArticles deleteArticle(final SeekaArticles article) {
		Session session = sessionFactory.getCurrentSession();
		session.update(article);
		return article;

	}	
	
	@Override
	public List<SeekaArticles> getAll(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, final List<BigInteger> categoryId, List<String> tags, Boolean status, Date filterDate) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SeekaArticles.class, "seeka_article");

		if (searchKeyword != null) {
			criteria.add(Restrictions.ilike("seeka_article.heading", searchKeyword, MatchMode.ANYWHERE));
		}
		
		if(categoryId != null && !categoryId.isEmpty()) {
			criteria.createAlias("category", "category");
			criteria.add(Restrictions.in("category.id", categoryId));
		}
		
		if(tags != null && !tags.isEmpty()) {
			criteria.add(Restrictions.in("tags", tags));
		}
		
		if(status != null) {
			criteria.add(Restrictions.in("published", status));
		}
		
		if(filterDate != null) {
			criteria.add(Restrictions.ge("createdAt", filterDate));
			criteria.add(Restrictions.ge("createdAt", LocalDateTime.from(filterDate.toInstant()).plusDays(1)));
		}

		if ( sortByType != null) {
			if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc(sortByField));
				} else {
					criteria.addOrder(Order.desc(sortByField));
				}
		}
		if (startIndex != null && pageSize != null) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}

		return criteria.list();
	}
	
	@Override
	public Integer getTotalSearchCount(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, List<BigInteger> categoryIdList, List<String> tagList, Boolean status, Date date) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SeekaArticles.class, "seeka_article");
		if (searchKeyword != null) {
			criteria.add(Restrictions.ilike("seeka_article.heading", searchKeyword, MatchMode.ANYWHERE));
		}
		if(categoryIdList != null && !categoryIdList.isEmpty()) {
			criteria.createAlias("category", "category");
			criteria.add(Restrictions.in("category.id", categoryIdList));
		}
		
		if(tagList != null && !tagList.isEmpty()) {
			criteria.add(Restrictions.in("tags", tagList));
		}
		
		if(status != null) {
			criteria.add(Restrictions.in("published", status));
		}
		
		if(date != null) {
			criteria.add(Restrictions.ge("createdAt", date));
			criteria.add(Restrictions.ge("createdAt", LocalDateTime.from(date.toInstant()).plusDays(1)));
		}
		if ( sortByType != null) {
			if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc(sortByField));
				} else {
					criteria.addOrder(Order.desc(sortByField));
				}
		}
		List<Object[]> rows = criteria.list();
		return rows.size();
	}

}
