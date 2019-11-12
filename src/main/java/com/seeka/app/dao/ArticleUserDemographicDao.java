package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleUserDemographic;

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
	public void deleteByArticleId(BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
	     String hql = "delete ArticleUserDemographic where article_id = :id";
	     Query q = session.createQuery(hql).setParameter("id", id);
	    q.executeUpdate();
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ArticleUserDemographic> getbyArticleId(BigInteger id) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ArticleUserDemographic.class, "article_user_deographic");
		criteria.createAlias("article", "article");
		criteria.add(Restrictions.eq("article.id", id));
		return criteria.list();
	}

}
