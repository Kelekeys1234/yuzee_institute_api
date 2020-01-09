package com.seeka.app.dao;

import java.math.BigInteger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.util.DateUtil;

@Repository
public class CurrencyRateDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("deprecation")
    public CurrencyRate getCurrencyRate(BigInteger fromCurrencyId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(CurrencyRate.class);
        crit.add(Restrictions.eq("baseCurrencyId", fromCurrencyId)).add(Restrictions.eq("createdDate", DateUtil.getUTCdatetimeAsStringYYYY_MM_DD()));
        return (CurrencyRate) crit.uniqueResult();
    }

    public void save(CurrencyRate currencyRate) {
        Session session = sessionFactory.getCurrentSession();
        session.save(currencyRate);
    }
}
