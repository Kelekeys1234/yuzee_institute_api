package com.seeka.app.dao;import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleFaculty;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.SeekaArticles;

@Repository
@SuppressWarnings("unchecked")
public class ArticleFacultyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveArticleFaculty(List<ArticleFaculty> list, BigInteger id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.faculty_id FROM article_faculty auc where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleFaculty bean = new ArticleFaculty();
                SeekaArticles articles = new SeekaArticles();
                articles.setId(id);
                bean.setSeekaArticles(articles);
                Faculty faculty = new Faculty();
                faculty.setId(new BigInteger((row[1].toString())));
                bean.setFaculty(faculty);
                bean.setId(new BigInteger((row[0].toString())));
                session.delete(bean);
            }
            for (ArticleFaculty bean : list) {
                session.save(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<Faculty> findByArticleId(BigInteger id) {
        List<Faculty> faculty = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                            .createSQLQuery("SELECT auc.id, auc.faculty_id, f.name FROM article_faculty auc inner join faculty f on auc.faculty_id = f.id where auc.article_id='"
                                            + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                Faculty bean = new Faculty();
                bean.setName(row[2].toString());
                bean.setId(new BigInteger((row[1].toString())));
                faculty.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return faculty;
    }
}
