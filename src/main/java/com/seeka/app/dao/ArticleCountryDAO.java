package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleCountry;
import com.seeka.app.dto.CountryDto;

@Repository
@SuppressWarnings("unchecked")
public class ArticleCountryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveArticleCountry(List<ArticleCountry> list, UUID id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.country_id FROM article_country auc where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleCountry bean = new ArticleCountry();
                bean.setArticleId(id);
                bean.setCountry(UUID.fromString((row[1].toString())));
                bean.setId(UUID.fromString((row[0].toString())));
                session.delete(bean);
            }
            for (ArticleCountry bean : list) {
                session.save(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<CountryDto> findByArticleId(UUID id) {
        List<CountryDto> countryDtos = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                            .createSQLQuery("SELECT auc.id, auc.country_id, c.name, c.country_code FROM article_country auc inner join country c with(nolock) on auc.country_id = c.id where auc.article_id='"
                                            + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                CountryDto bean = new CountryDto();
                bean.setName((row[2].toString()));
                bean.setId(UUID.fromString((row[1].toString())));
                bean.setCountryCode((row[3].toString()));
                countryDtos.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return countryDtos;
    }
}
