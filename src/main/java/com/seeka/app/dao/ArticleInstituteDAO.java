package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleInstitute;
import com.seeka.app.dto.InstituteResponseDto;

@Repository
@SuppressWarnings("unchecked")
public class ArticleInstituteDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveArticleInstitute(List<ArticleInstitute> list, UUID id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.institute_id FROM article_institute auc where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleInstitute bean = new ArticleInstitute();
                bean.setArticleId(id);
                bean.setInstituteId(UUID.fromString((row[1].toString())));
                bean.setId(UUID.fromString((row[0].toString())));
                session.delete(bean);
            }
            for (ArticleInstitute bean : list) {
                session.save(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<InstituteResponseDto> findByArticleId(UUID id) {
        List<InstituteResponseDto> institute = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.institute_id, c.name FROM article_institute auc inner join institute c with(nolock) on auc.institute_id = c.id where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                InstituteResponseDto bean = new InstituteResponseDto();
                bean.setInstituteName(row[2].toString());
                bean.setInstituteId(UUID.fromString((row[1].toString())));
                institute.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return institute;
    }
}
