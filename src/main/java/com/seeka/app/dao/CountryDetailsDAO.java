package com.seeka.app.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CountryDetails;

@Repository
public class CountryDetailsDAO implements ICountryDetailsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(CountryDetails countryDetails) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(countryDetails);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
