package com.seeka.app.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CountryImages;

@Repository
public class CountryImageDAO implements ICountryImageDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(CountryImages countryImages) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(countryImages);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
