package com.yuzee.app.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Articles;

@Repository
@SuppressWarnings({ "unchecked", "deprecation" })
public class ArticleDAO implements IArticleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Integer getTotalSearchCount(final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Articles.class, "seeka_article");
		if (searchKeyword != null) {
			criteria.add(Restrictions.ilike("seeka_article.heading", searchKeyword, MatchMode.ANYWHERE));
		}
		List<Object[]> rows = criteria.list();
		return rows.size();
	}

	@Override
	public Articles save(final Articles article) {
		Session session = sessionFactory.getCurrentSession();
		session.save(article);
		return article;
	}

	@Override
	public Articles findById(final String uId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Articles.class, uId);
	}

	@Override
	public Articles deleteArticle(final Articles article) {
		Session session = sessionFactory.getCurrentSession();
		session.update(article);
		return article;

	}

	@Override
	public List<Articles> getAll(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, final List<String> categoryId, final List<String> tags, final Boolean status, final Date filterDate) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Articles.class, "seeka_article");

		if (searchKeyword != null) {
			criteria.add(Restrictions.ilike("seeka_article.heading", searchKeyword, MatchMode.ANYWHERE));
		}

		if ((categoryId != null) && !categoryId.isEmpty()) {
			criteria.createAlias("category", "category");
			criteria.add(Restrictions.in("category.id", categoryId));
		}

		if ((tags != null) && !tags.isEmpty()) {
			criteria.add(Restrictions.in("tags", tags));
		}

		if (status != null) {
			criteria.add(Restrictions.in("published", status));
		}

		if (filterDate != null) {
			criteria.add(Restrictions.ge("createdAt", filterDate));
			criteria.add(Restrictions.ge("createdAt", LocalDateTime.from(filterDate.toInstant()).plusDays(1)));
		}

		if (sortByType != null) {
			if ("ASC".equals(sortByType)) {
				criteria.addOrder(Order.asc(sortByField));
			} else {
				criteria.addOrder(Order.desc(sortByField));
			}
		} else {
			if ("ASC".equals(sortByType)) {
				criteria.addOrder(Order.asc("seeka_article.heading"));
			} else {
				criteria.addOrder(Order.desc("seeka_article.heading"));
			}
		}
		if ((startIndex != null) && (pageSize != null)) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}

		return criteria.list();
	}

	@Override
	public Integer getTotalSearchCount(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, final List<String> categoryIdList, final List<String> tagList, final Boolean status, final Date date) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Articles.class, "seeka_article");
		if (searchKeyword != null) {
			criteria.add(Restrictions.ilike("seeka_article.heading", searchKeyword, MatchMode.ANYWHERE));
		}
		if ((categoryIdList != null) && !categoryIdList.isEmpty()) {
			criteria.createAlias("category", "category");
			criteria.add(Restrictions.in("category.id", categoryIdList));
		}

		if ((tagList != null) && !tagList.isEmpty()) {
			criteria.add(Restrictions.in("tags", tagList));
		}

		if (status != null) {
			criteria.add(Restrictions.in("published", status));
		}

		if (date != null) {
			criteria.add(Restrictions.ge("createdAt", date));
			criteria.add(Restrictions.ge("createdAt", LocalDateTime.from(date.toInstant()).plusDays(1)));
		}
		if (sortByType != null) {
			if ("ASC".equals(sortByType)) {
				criteria.addOrder(Order.asc(sortByField));
			} else {
				criteria.addOrder(Order.desc(sortByField));
			}
		}
		List<Object[]> rows = criteria.list();
		return rows.size();
	}

	@Override
	public List<String> getAuthors(final int startIndex, final Integer pageSize, final String searchString) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Articles.class, "article");
		if ((searchString != null) && !searchString.isEmpty() && !"".equalsIgnoreCase(searchString.trim())) {
			criteria.add(Restrictions.ilike("article.author", searchString, MatchMode.ANYWHERE));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(pageSize);
		criteria.add(Restrictions.isNotNull("article.author"));
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.distinct(Projections.property("article.author")));
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("article.postDate"));
		return criteria.list();
	}

	@Override
	public int getTotalAuthorCount(final String searchString) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Articles.class, "article");
		if ((searchString != null) && !searchString.isEmpty() && !"".equalsIgnoreCase(searchString.trim())) {
			criteria.add(Restrictions.ilike("article.author", searchString, MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.isNotNull("article.author"));
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.distinct(Projections.property("article.author")));
		criteria.setProjection(projectionList);
		List<String> count = criteria.list();
		return count != null ? count.size() : 0;
	}

	@Override
	public List<Articles> findArticleByCountryId(final String countryId, final String categoryName, final Integer count,
			final List<String> viewArticleIds) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Articles.class, "article");
		criteria.createAlias("article.country", "country");
		criteria.createAlias("article.category", "category");
		criteria.add(Restrictions.in("country.id", countryId));
		criteria.add(Restrictions.in("category.name", categoryName));
		criteria.add(Restrictions.not(Restrictions.in("article.id", viewArticleIds)));
		criteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
		criteria.setFirstResult(0);
		criteria.setMaxResults(count);
		return criteria.list();
	}

}
