package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public CountryDetails getDetailsByCountryId(BigInteger id) {
        CountryDetails countryDetail = null;
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(CountryDetails.class);
        crit.add(Restrictions.eq("country.id", id));
        List<CountryDetails> countryDetails = crit.list();
        if (countryDetails != null) {
            countryDetail = countryDetails.get(0);
        }
        return countryDetail;
    }
}
