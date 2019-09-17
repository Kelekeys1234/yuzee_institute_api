package com.seeka.app.dao;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Currency;
import com.seeka.app.bean.HelpCategory;
import com.seeka.app.bean.HelpSubCategory;
import com.seeka.app.bean.SeekaHelp;

@Repository
public class HelpDAO implements IHelpDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(SeekaHelp seekaHelp) {
        Session session = sessionFactory.getCurrentSession();
        session.save(seekaHelp);
    }

    @Override
    public HelpCategory getHelpCategory(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        HelpCategory obj = session.get(HelpCategory.class, id);
        return obj;
    }

    @Override
    public HelpSubCategory getHelpSubCategory(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        HelpSubCategory obj = session.get(HelpSubCategory.class, id);
        return obj;
    }

    @Override
    public SeekaHelp get(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        SeekaHelp obj = session.get(SeekaHelp.class, id);
        return obj;
    }

    @Override
    public void update(SeekaHelp seekaHelp) {
        Session session = sessionFactory.getCurrentSession();
        session.update(seekaHelp);
    }
    
    

}
