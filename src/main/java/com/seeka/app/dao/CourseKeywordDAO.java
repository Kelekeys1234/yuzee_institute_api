package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CourseKeyword;
import com.seeka.app.dto.CourseResponseDto;

@Repository
@SuppressWarnings("unchecked")
public class CourseKeywordDAO implements ICourseKeywordDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(CourseKeyword obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    @Override
    public void update(CourseKeyword obj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }

    @Override
    public List<CourseKeyword> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(CourseKeyword.class);
        return crit.list();
    }

    @Override
    public List<CourseKeyword> searchCourseKeyword(String keyword) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("SELECT sk.keyword FROM search_keywords sk WHERE sk.keyword LIKE '%" + keyword + "%'");
        return query.list();
    }
}
