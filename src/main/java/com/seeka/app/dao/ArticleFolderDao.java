package com.seeka.app.dao;import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleFolder;

@Repository
@SuppressWarnings("unchecked")
public class ArticleFolderDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(ArticleFolder articleFolder) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(articleFolder);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public ArticleFolder findById(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(ArticleFolder.class, id);
    }

    public List<ArticleFolder> getAllArticleFolder() {
        List<ArticleFolder> articleFolders = new ArrayList<ArticleFolder>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.folder_name FROM article_folder auc where auc.deleted=1");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleFolder bean = new ArticleFolder();
                bean.setFolderName((row[1].toString()));
                bean.setId(new BigInteger((row[0].toString())));
                articleFolders.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return articleFolders;
    }

    public List<ArticleFolder> getAllArticleFolderByUserId(BigInteger userId) {
        List<ArticleFolder> articleFolders = new ArrayList<ArticleFolder>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.folder_name FROM article_folder auc where auc.deleted=1 and auc.user_id='" + userId + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleFolder bean = new ArticleFolder();
                bean.setFolderName((row[1].toString()));
                bean.setId(new BigInteger((row[0].toString())));
                articleFolders.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return articleFolders;
    }
}
