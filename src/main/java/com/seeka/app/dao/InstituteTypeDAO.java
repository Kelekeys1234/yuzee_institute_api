package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}
