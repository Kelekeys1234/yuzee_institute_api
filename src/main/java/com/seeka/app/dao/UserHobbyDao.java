package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Hobbies;
import com.seeka.app.bean.UserBiginterestCountry;
import com.seeka.app.bean.UserInterestHobbies;

@Repository
@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class UserHobbyDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(UserInterestHobbies interestHobbies) {
        Session session = sessionFactory.getCurrentSession();
        session.save(interestHobbies);
    }

    public List<Hobbies> getUserHobbies(BigInteger userId) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select h.id as id , h.hobby_txt FROM user_interest_hobbies uih inner join hobbies h on uih.hobby_id = h.id where uih.user_id= " + userId;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        Hobbies obj = null;
        for (Object[] row : rows) {
            obj = new Hobbies();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setHobbyTxt(row[1].toString());
            hobbies.add(obj);
        }
        return hobbies;
    }

    public int findTotalCountByUser(BigInteger userId) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select hb.id from user_interest_hobbies hb where hb.user_id=" + userId;
        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        return rows.size();
    }

    public void deleteUserHobbies(BigInteger userId, BigInteger hobbyId) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select uih.id , uih.user_id FROM user_interest_hobbies uih where uih.user_id= " + userId + " and uih.hobby_id=" + hobbyId;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        UserInterestHobbies obj = null;
        for (Object[] row : rows) {
            obj = new UserInterestHobbies();
            obj.setId(new BigInteger((row[0].toString())));
            session.delete(obj);
        }
    }

    public void saveUserCountry(UserBiginterestCountry userBiginterestCountry) {
        Session session = sessionFactory.getCurrentSession();
        session.save(userBiginterestCountry);
    }

    public List<String> getCountryByUserId(BigInteger userId) {
        List<String> countries = new ArrayList<String>();
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select uih.id , uih.country_name FROM user_biginterest_country uih where uih.user_id= " + userId;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        for (Object[] row : rows) {
            countries.add((row[1].toString()));
        }
        return countries;
    }

    public void deleteUserCountry(BigInteger userId, String countryId) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select uih.id , uih.user_id FROM user_biginterest_country uih where uih.user_id= " + userId + " and uih.country_name= '" + countryId + "'";
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        UserBiginterestCountry obj = null;
        for (Object[] row : rows) {
            obj = new UserBiginterestCountry();
            obj.setId(new BigInteger((row[0].toString())));
            session.delete(obj);
        }
    }
}
