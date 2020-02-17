package com.seeka.app.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserEducationAOLevelSubjects;

@Repository
@SuppressWarnings("deprecation")
public class UserEducationAOLevelSubjectDAO implements IUserEducationAOLevelSubjectDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(UserEducationAOLevelSubjects hobbiesObj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(hobbiesObj);
    }

    @Override
    public void update(UserEducationAOLevelSubjects hobbiesObj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(hobbiesObj);
    }

    @Override
    public UserEducationAOLevelSubjects get(String id) {
        Session session = sessionFactory.getCurrentSession();
        UserEducationAOLevelSubjects obj = session.get(UserEducationAOLevelSubjects.class, id);
        return obj;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserEducationAOLevelSubjects> getUserLevelSubjectGrades(String userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(UserEducationAOLevelSubjects.class);
        crit.add(Restrictions.eq("userId", userId));
        return crit.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserEducationAOLevelSubjects> getActiveUserLevelSubjectGrades(String userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(UserEducationAOLevelSubjects.class);
        crit.add(Restrictions.eq("userId", userId)).add(Restrictions.eq("isActive", true));
        return crit.list();
    }

    public void deleteEducationAOLevelByUserId(String userId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("delete from UserEducationAOLevelSubjects where user_id =" + userId);
        q.executeUpdate();
    }
}
