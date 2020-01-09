package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseMinRequirement;
import com.seeka.app.bean.Subject;

@Repository
public class CourseMinRequirementDao implements ICourseMinRequirementDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(CourseMinRequirement obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    @Override
    public List<CourseMinRequirement> get(BigInteger course) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(CourseMinRequirement.class);
        crit.add(Restrictions.eq("course.id", course));
        return crit.list();
    }
    
    
}
