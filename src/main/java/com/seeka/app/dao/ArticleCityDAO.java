package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleCity;
import com.seeka.app.bean.City;

@Repository
@SuppressWarnings("unchecked")
public class ArticleCityDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveArticleCity(List<ArticleCity> list, UUID id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.city_id FROM article_city auc where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleCity bean = new ArticleCity();
                bean.setArticleId(id);
                bean.setCity(UUID.fromString((row[1].toString())));
                bean.setId(UUID.fromString((row[0].toString())));
                session.delete(bean);
            }
            for (ArticleCity bean : list) {
                session.save(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<City> findByArticleId(UUID id) {
        List<City> city = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.city_id, c.name FROM article_city auc inner join city c with(nolock) on auc.city_id = c.id where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                City bean = new City();
                bean.setName((row[2].toString()));
                bean.setId(UUID.fromString((row[1].toString())));
                city.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return city;
    }
}
