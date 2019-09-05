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

import com.seeka.app.bean.Hobbies;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.Intake;

@Repository
public class InstituteTypeDAO implements IInstituteTypeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(InstituteType instituteType) {
        Session session = sessionFactory.getCurrentSession();
        session.save(instituteType);
    }

    @Override
    public void update(InstituteType instituteType) {
        Session session = sessionFactory.getCurrentSession();
        session.update(instituteType);
    }

    @Override
    public InstituteType get(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(InstituteType.class, id);
    }

    @Override
    public List<Intake> getAllIntake() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Intake.class);
        return crit.list();
    }

    @Override
    public List<InstituteType> getAll() {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select h.id as id , h.name FROM institute_type h";
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<InstituteType> hobbies = new ArrayList<InstituteType>();
        InstituteType obj = null;
        for (Object[] row : rows) {
            obj = new InstituteType();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setName(row[1].toString());
            hobbies.add(obj);
        }
        return hobbies;
    }

}
