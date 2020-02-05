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

@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
@Repository
public class InterestDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Interest> getAll() {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select h.id as id , h.interest FROM interest h where h.deleted_on IS NULL ORDER BY h.created_on DESC ";
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Interest> interests = new ArrayList<Interest>();
        Interest obj = null;
        for (Object[] row : rows) {
            obj = new Interest();
            obj.setId(row[0].toString());
            obj.setInterest(row[1].toString());
            interests.add(obj);
        }
        return interests;
    }

    public List<Interest> searchInterest(String searchText) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select h.id as id , h.interest FROM interest h where h.deleted_on IS NULL and h.interest like '%" + searchText.trim() + "%' ORDER BY h.created_on DESC ";
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Interest> interests = new ArrayList<Interest>();
        Interest obj = null;
        for (Object[] row : rows) {
            obj = new Interest();
            obj.setId(row[0].toString());
            obj.setInterest(row[1].toString());
            interests.add(obj);
        }
        return interests;
    }

    public Interest get(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        Interest obj = session.get(Interest.class, id);
        return obj;
    }
}
