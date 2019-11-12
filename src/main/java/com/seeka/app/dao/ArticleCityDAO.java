package com.seeka.app.dao;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.seeka.app.bean.ArticleCity;
import com.seeka.app.bean.ArticleCountry;
import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.SeekaArticles;

@Repository
@SuppressWarnings("unchecked")
public class ArticleCityDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveArticleCity(List<ArticleCity> list, BigInteger id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.city_id FROM article_city auc where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleCity bean = new ArticleCity();
                SeekaArticles articles = new SeekaArticles();
                articles.setId(id);

                City city = new City();
                city.setId(new BigInteger((row[1].toString())));

                bean.setSeekaArticles(articles);
                bean.setCity(city);
                bean.setId(new BigInteger((row[0].toString())));
                session.delete(bean);
            }
            for (ArticleCity bean : list) {
                session.save(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<City> findByArticleId(BigInteger id) {
        List<City> city = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(
                            "SELECT auc.id, auc.city_id, c.name FROM article_city auc inner join city c on auc.city_id = c.id where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                City bean = new City();
                bean.setName((row[2].toString()));
                bean.setId(new BigInteger((row[1].toString())));
                city.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return city;
    }

	public void addArticleCity(List<City> cityList, SeekaArticles article) {
		 Session session = sessionFactory.getCurrentSession();
		 for (City city : cityList) {
			  ArticleCity bean = new ArticleCity();
			  bean.setCity(city);
			  bean.setSeekaArticles(article);
             session.save(bean);
         }  
		
	}
}
