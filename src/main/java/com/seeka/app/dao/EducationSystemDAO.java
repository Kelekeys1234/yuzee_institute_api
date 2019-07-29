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

import com.seeka.app.bean.EducationSystem;

@Repository
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
public class EducationSystemDAO implements IEducationSystemDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(EducationSystem hobbiesObj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(hobbiesObj);
    }

    @Override
    public void update(EducationSystem hobbiesObj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(hobbiesObj);
    }

    @Override
    public EducationSystem get(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        EducationSystem obj = session.get(EducationSystem.class, id);
        return obj;
    }

    @Override
    public List<EducationSystem> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(EducationSystem.class);
        return crit.list();
    }

    @Override
    public List<EducationSystem> getAllGlobeEducationSystems() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select id,name,code,country_id,is_active from education_system  where country_id is null " + "and is_active = 1 order by code");
        List<Object[]> rows = query.list();
        List<EducationSystem> list = new ArrayList<>();
        for (Object[] row : rows) {
            EducationSystem obj = new EducationSystem();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setName(row[1].toString());
            obj.setCode(row[2].toString());
            list.add(obj);
        }
        return list;
    }

    @Override
    public List<EducationSystem> getEducationSystemsByCountryId(BigInteger countryId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session
                        .createSQLQuery("select id, name, code ,country_id ,is_active from education_system  where country_id=" + countryId + " and is_active = 1 order by code");
        List<Object[]> rows = query.list();
        List<EducationSystem> list = new ArrayList<>();
        for (Object[] row : rows) {
            EducationSystem obj = new EducationSystem();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setName(row[1].toString());
            obj.setCode(row[2].toString());
            list.add(obj);
        }
        return list;
    }
}