package com.seeka.app.dao;import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleInstitute;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.dto.InstituteResponseDto;

@Repository
@SuppressWarnings("unchecked")
public class ArticleInstituteDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveArticleInstitute(List<ArticleInstitute> list, BigInteger id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.institute_id FROM article_institute auc where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleInstitute bean = new ArticleInstitute();
                SeekaArticles articles = new SeekaArticles();
                articles.setId(id);
                bean.setSeekaArticles(articles);
                
                Institute institute = new Institute();
                institute.setId(new BigInteger((row[1].toString())));
                bean.setInstitute(institute);
                bean.setId(new BigInteger((row[0].toString())));
                session.delete(bean);
            }
            for (ArticleInstitute bean : list) {
                session.save(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<InstituteResponseDto> findByArticleId(BigInteger id) {
        List<InstituteResponseDto> institute = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                            .createSQLQuery("SELECT auc.id, auc.institute_id, c.name FROM article_institute auc inner join institute c  on auc.institute_id = c.id where auc.article_id='"
                                            + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                InstituteResponseDto bean = new InstituteResponseDto();
                bean.setInstituteName(row[2].toString());
                bean.setInstituteId(new BigInteger((row[1].toString())));
                institute.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return institute;
    }
}
