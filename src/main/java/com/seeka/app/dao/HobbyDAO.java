package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Hobbies;

@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
@Repository
public class HobbyDAO implements IHobbyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Hobbies hobbiesObj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(hobbiesObj);
    }

    @Override
    public void update(Hobbies hobbiesObj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(hobbiesObj);
    }

    @Override
    public Hobbies get(String id) {
        Session session = sessionFactory.getCurrentSession();
        Hobbies obj = session.get(Hobbies.class, id);
        return obj;
    }

    @Override
    public List<Hobbies> searchByHobbies(String hobbyTxt) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "SELECT h.id,h.hobby_txt FROM hobbies h WHERE 1=1 ";
        if (null != hobbyTxt && !hobbyTxt.isEmpty()) {
            sqlQuery = "and h.hobby_txt LIKE '%" + hobbyTxt + "%'";
        }

        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        Hobbies obj = null;
        for (Object[] row : rows) {
            obj = new Hobbies();
            obj.setId(row[0].toString());
            obj.setHobbyTxt(row[1].toString());
            hobbies.add(obj);
        }
        return hobbies;
    }

    @Override
    public int findTotalCount() {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select hb.id from hobbies hb where hb.deleted_on IS NULL";
        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        return rows.size();
    }

    @Override
    public List<Hobbies> getAll() {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select h.id as id , h.hobby_txt FROM hobbies h where h.deleted_on IS NULL ORDER BY h.created_on DESC ";
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        Hobbies obj = null;
        for (Object[] row : rows) {
            obj = new Hobbies();
            obj.setId(row[0].toString());
            obj.setHobbyTxt(row[1].toString());
            hobbies.add(obj);
        }
        return hobbies;
    }

    @Override
    public List<Hobbies> searchHobbies(String searchText) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select h.id as id , h.hobby_txt FROM hobbies h where h.deleted_on IS NULL and h.hobby_txt like '%" + searchText.trim()
                        + "%' ORDER BY h.created_on DESC ";
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        Hobbies obj = null;
        for (Object[] row : rows) {
            obj = new Hobbies();
            obj.setId(row[0].toString());
            obj.setHobbyTxt(row[1].toString());
            hobbies.add(obj);
        }
        return hobbies;
    }

    @Override
    public List<Hobbies> autoSearch(int pageNumber, int pageSize, String searchKey) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select h.id as id , h.hobby_txt FROM hobbies h where h.deleted_on IS NULL and h.hobby_txt like '%" + searchKey.trim() + "%' ORDER BY h.created_on DESC ";
        sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        Hobbies obj = null;
        for (Object[] row : rows) {
            obj = new Hobbies();
            obj.setId(row[0].toString());
            obj.setHobbyTxt(row[1].toString());
            hobbies.add(obj);
        }
        return hobbies;
    }
}
