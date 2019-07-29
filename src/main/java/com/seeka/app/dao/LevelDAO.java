package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Level;

@Repository
public class LevelDAO implements ILevelDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Level obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    @Override
    public void update(Level obj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }

    @Override
    public Level get(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        Level obj = session.get(Level.class, id);
        return obj;
    }

    @Override
    public List<Level> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Level.class);
        return crit.list();
    }

    @Override
    public List<Level> getCourseTypeByCountryId(BigInteger countryID) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select distinct ct.id, ct.type_txt as courseType from course_type ct  inner join faculty f  on f.course_type_id = ct.id "
                        + "inner join course c  on c.faculty_id = f.id inner join institute_course ic  on ic.course_id = c.id " + "where ic.country_id = :countryId")
                        .setParameter("countryId", countryID);
        List<Object[]> rows = query.list();
        List<Level> courseTypes = new ArrayList<>();
        for (Object[] row : rows) {
            Level obj = new Level();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setName(row[1].toString());
            courseTypes.add(obj);
        }
        return courseTypes;
    }

    @Override
    public List<Level> getLevelByCountryId(BigInteger countryId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select distinct le.id, le.name as name,le.code as levelkey from level le  inner join institute_level il  on il.level_id = le.id "
                        + "inner join country c  on c.id = il.country_id " + "where il.country_id = :countryId").setParameter("countryId", countryId);

        List<Object[]> rows = query.list();

        List<Level> level = new ArrayList<>();

        for (Object[] row : rows) {
            Level obj = new Level();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setName(row[1].toString());
            obj.setCode(row[2].toString());
            level.add(obj);
        }
        return level;
    }

    @Override
    public List<Level> getAllLevelByCountry() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select distinct le.id, le.name as name,le.code as levelkey,il.country_id from level le  inner join institute_level il  on "
                        + "il.level_id = le.id inner join country c  on c.id = il.country_id");
        List<Object[]> rows = query.list();

        List<Level> level = new ArrayList<>();

        for (Object[] row : rows) {
            Level obj = new Level();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setName(row[1].toString());
            if (row[2] != null) {
                obj.setCode(row[2].toString());
            }
            obj.setCountryId(new BigInteger(row[3].toString()));
            level.add(obj);
        }
        return level;
    }

}
