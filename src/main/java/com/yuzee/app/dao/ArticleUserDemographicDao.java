package com.yuzee.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.ArticleUserDemographic;

@Repository
public class ArticleUserDemographicDao implements IArticleUserDemographicDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public ArticleUserDemographic save(ArticleUserDemographic articleUserDemo) {
		Session session = sessionFactory.getCurrentSession();
		session.save(articleUserDemo);
		return articleUserDemo;
	}

	@Override
	public void deleteByArticleId(String id) {
		Session session = sessionFactory.getCurrentSession();
	     String hql = "delete ArticleUserDemographic where article_id = :id";
	     Query q = session.createQuery(hql).setParameter("id", id);
	    q.executeUpdate();
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ArticleUserDemographic> getbyArticleId(String id) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ArticleUserDemographic.class, "article_user_deographic");
		criteria.createAlias("article", "article");
		criteria.add(Restrictions.eq("article.id", id));
		return criteria.list();
	}

	@Override
	public List<ArticleUserDemographic> getArticleCityListbyCountryId(String id, String articleId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ArticleUserDemographic.class, "article_user_deographic");
		criteria.createAlias("country", "country");
		criteria.add(Restrictions.eq("country.id", id));
		criteria.createAlias("article", "article");
		criteria.add(Restrictions.eq("article.id", articleId));
		return criteria.list();

	}

}
