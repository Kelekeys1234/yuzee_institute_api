package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Interest;
import com.seeka.app.bean.UserInterest;

@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
@Repository
public class UserInterestDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(UserInterest interestHobbies) {
        Session session = sessionFactory.getCurrentSession();
        session.save(interestHobbies);

    }

    public List<Interest> getUserInterest(BigInteger userId) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select h.id as id , h.interest FROM user_interest uih inner join interest h on uih.interest_id = h.id where uih.user_id= " + userId;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Interest> hobbies = new ArrayList<Interest>();
        Interest obj = null;
        for (Object[] row : rows) {
            obj = new Interest();
            obj.setId(row[0].toString());
            obj.setInterest(row[1].toString());
            hobbies.add(obj);
        }
        return hobbies;
    }

    public void deleteUserInterest(BigInteger userId, BigInteger interestId) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select uih.id , uih.user_id FROM user_interest uih where uih.user_id= " + userId + " and uih.interest_id=" + interestId;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        UserInterest obj = null;
        for (Object[] row : rows) {
            obj = new UserInterest();
            obj.setId(row[0].toString());
            session.delete(obj);
        }
    }
}
