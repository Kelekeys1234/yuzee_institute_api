package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleFolderMap;
import com.seeka.app.dto.ArticleNameDto;

@SuppressWarnings("deprecation")
@Repository
public class ArticleFolderMapDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(ArticleFolderMap articleFolderMap) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(articleFolderMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<ArticleNameDto> getFolderArticles(BigInteger id) {
        List<ArticleNameDto> articleNameDtos = new ArrayList<ArticleNameDto>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(
                            "SELECT sc.id, sc.heading FROM article_folder_map auc inner join seeka_articles sc on auc.article_id = sc.id  where auc.folder_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleNameDto bean = new ArticleNameDto();
                bean.setTitle((row[1].toString()));
                bean.setArticleId((new BigInteger((row[0].toString()))));
                articleNameDtos.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return articleNameDtos;
    }

    @SuppressWarnings("unchecked")
    public String getFolderImageUrl(BigInteger id) {
        String imageUrl = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(
                            "SELECT sc.imagepath FROM article_folder_map auc inner join seeka_articles sc on auc.article_id = sc.id  where auc.folder_id='" + id + "'"
                                            + "ORDER BY auc.article_id DESC LIMIT 1");
             imageUrl = (String) query.uniqueResult();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return imageUrl;
    }
    
    @SuppressWarnings("unchecked")
    public List<ArticleFolderMap> getArticleByFolderId(BigInteger folderId) {
        List<ArticleFolderMap> articleFolderMaps = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(
                            "SELECT auc.id , auc.folder_id , auc.article_id FROM article_folder_map auc where auc.folder_id=" + folderId + " GROUP BY auc.article_id");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleFolderMap bean = new ArticleFolderMap();
                bean.setId((new BigInteger((row[0].toString()))));
                bean.setArticleId((new BigInteger((row[2].toString()))));
                bean.setFolderId((new BigInteger((row[1].toString()))));
                articleFolderMaps.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return articleFolderMaps;
    }
}
