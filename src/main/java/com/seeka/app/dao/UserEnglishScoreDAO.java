package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserEnglishScore;

@Repository
@SuppressWarnings({ "deprecation", "unchecked" })
public class UserEnglishScoreDAO implements IUserEnglishScoreDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(UserEnglishScore obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    @Override
    public void update(UserEnglishScore obj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }

    @Override
    public List<UserEnglishScore> getAll() {
        System.out.println("Inside UserEnglishScore DAO....");
        Session session = sessionFactory.getCurrentSession();
        List<UserEnglishScore> list = session.createCriteria(UserEnglishScore.class).list();
        return list;
    }

    @Override
    public UserEnglishScore get(BigInteger id) {
        System.out.println("BigInteger : " + id);
        Session session = sessionFactory.getCurrentSession();
        UserEnglishScore UserEnglishScore = session.get(UserEnglishScore.class, id);
        return UserEnglishScore;
    }

    @Override
    public List<UserEnglishScore> getEnglishEligibiltyByUserID(String userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(UserEnglishScore.class);
        crit.add(Restrictions.eq("userId", userId));
        return crit.list();
    }

    public void deleteEnglishScoreByUserId(String userId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("delete from UserEduIelTofScore where user_id =" + userId);
        q.executeUpdate();
    }
}
