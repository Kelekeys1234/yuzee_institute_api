package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleUserCitizenship;
import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;

@Repository
public class UserArticleDAO implements IUserArticleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveArticleUserCitizenship(ArticleUserCitizenship userCitizenship) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(
                            "SELECT auc.id, auc.country_id FROM article_user_citizenship auc where auc.article_id='" + userCitizenship.getSeekaArticles().getId() + "'");
            @SuppressWarnings("unchecked")
            List<Object[]> rows = query.list();
            ArticleUserCitizenship articleUserCitizenship = null;
            for (Object[] row : rows) {
                articleUserCitizenship = new ArticleUserCitizenship();
                articleUserCitizenship.setId(new BigInteger((row[0].toString())));
                Country country = new Country();
                country.setId(new BigInteger((row[1].toString())));

                articleUserCitizenship.setCountry(country);
            }
            if (articleUserCitizenship != null && articleUserCitizenship.getId() != null) {
                userCitizenship.setId(articleUserCitizenship.getId());
                session.update(userCitizenship);
            } else {
                session.save(userCitizenship);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public ArticleUserCitizenship findArticleUserCitizenshipDetails(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("SELECT auc.country_id, auc.city_id as cityId FROM article_user_citizenship auc");
        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.list();
        ArticleUserCitizenship articleUserCitizenship = null;
        for (Object[] row : rows) {
            articleUserCitizenship = new ArticleUserCitizenship();
            Country country = new Country();
            country.setId(new BigInteger((row[0].toString())));
            articleUserCitizenship.setCountry(country);

            City city = new City();
            city.setId(new BigInteger((row[1].toString())));
            articleUserCitizenship.setCity(city);
        }
        return articleUserCitizenship;
    }
}
