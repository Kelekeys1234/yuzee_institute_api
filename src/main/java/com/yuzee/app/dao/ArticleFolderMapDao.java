package com.yuzee.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.ArticleFolderMap;


@Repository
public class ArticleFolderMapDao implements IArticleFolderMapDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public ArticleFolderMap save(ArticleFolderMap articleFolderMap) {
            Session session = sessionFactory.getCurrentSession();
            session.save(articleFolderMap);
           return articleFolderMap;
    }
    @Override
    public List<ArticleFolderMap> getArticleByFolderId(Integer startIndex,Integer pageSize, String folderId) {
        Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ArticleFolderMap.class, "article_folder_mapping");
		criteria.add(Restrictions.eq("folderId", folderId));
		if (startIndex != null && pageSize != null) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}
		return criteria.list();
    }
}
