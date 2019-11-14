package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleFolder;

@Repository
@SuppressWarnings({ "unchecked", "deprecation" })
public class ArticleFolderDao implements IArticleFolderDao{

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void save(ArticleFolder articleFolder) {
            Session session = sessionFactory.getCurrentSession();
            session.save(articleFolder);
    }

    @Override
    public ArticleFolder findById(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(ArticleFolder.class, id);
    }

    @Override
    public List<ArticleFolder> getAllArticleFolder() {        
            Session session = sessionFactory.getCurrentSession();
        	Criteria criteria = session.createCriteria(ArticleFolder.class);
    		criteria.add(Restrictions.eq("deleted", true));
    		return criteria.list();
    }

    @Override
    public List<ArticleFolder> getAllArticleFolderByUserId(BigInteger userId) {
    	 Session session = sessionFactory.getCurrentSession();
     	Criteria criteria = session.createCriteria(ArticleFolder.class);
 		criteria.add(Restrictions.eq("userId",userId));
 		return criteria.list();
    }

	@Override
	public List<ArticleFolder> getAllFolderByUserId(BigInteger userId) {
		 Session session = sessionFactory.getCurrentSession();
	     	Criteria criteria = session.createCriteria(ArticleFolder.class);
	 		criteria.add(Restrictions.eq("userId",userId));
	 		return criteria.list();  
	}

}
