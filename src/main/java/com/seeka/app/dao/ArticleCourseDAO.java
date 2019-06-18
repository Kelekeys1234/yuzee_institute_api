package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleCourse;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.dto.CourseDto;

@Repository
@SuppressWarnings("unchecked")
public class ArticleCourseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveArticleCorses(List<ArticleCourse> list, BigInteger id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("SELECT auc.id, auc.course_id FROM article_course auc where auc.article_id='" + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                ArticleCourse bean = new ArticleCourse();
                SeekaArticles articles = new SeekaArticles();
                articles.setId(id);
                bean.setSeekaArticles(articles);
                Course course = new Course();
                course.setId(new BigInteger((row[1].toString())));
                bean.setCourse(course);
                bean.setId(new BigInteger((row[0].toString())));
                session.delete(bean);
            }
            for (ArticleCourse bean : list) {
                session.save(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<CourseDto> findByArticleId(BigInteger id) {
        List<CourseDto> courses = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session
                            .createSQLQuery("SELECT auc.id, auc.course_id , c.name FROM article_course auc inner join course c on auc.course_id = c.id where auc.article_id='"
                                            + id + "'");
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                CourseDto bean = new CourseDto();
                bean.setCourseId(new BigInteger((row[1].toString())));
                bean.setCourseName((row[2].toString()));
                courses.add(bean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return courses;
    }
}
