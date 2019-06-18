package com.seeka.app.dao;import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleFolderMap;
import com.seeka.app.dto.ArticleNameDto;

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
            Query query = session
                            .createSQLQuery("SELECT sc.id, sc.heading FROM article_folder_map auc inner join seeka_articles sc on auc.article_id = sc.id  where auc.folder_id='"
                                            + id + "'");
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
}
