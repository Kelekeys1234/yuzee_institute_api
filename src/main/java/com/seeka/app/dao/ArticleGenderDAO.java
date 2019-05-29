package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleGender;
import com.seeka.app.dto.GenderDto;

@Repository
@SuppressWarnings("unchecked")
public class ArticleGenderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveArticleGender(List<ArticleGender> list, UUID id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.gender FROM article_gender auc where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleGender bean = new ArticleGender();
                bean.setArticleId(id);
                bean.setGender((row[1].toString()));
                bean.setId(UUID.fromString((row[0].toString())));
                session.delete(bean);
            }
            for (ArticleGender articleGender : list) {
                session.save(articleGender);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<GenderDto> findByArticleId(UUID id) {
        List<GenderDto> gender = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.gender FROM article_gender auc where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                GenderDto bean = new GenderDto();
                bean.setName(row[1].toString());
                bean.setId(row[1].toString());
                gender.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return gender;
    }
}
