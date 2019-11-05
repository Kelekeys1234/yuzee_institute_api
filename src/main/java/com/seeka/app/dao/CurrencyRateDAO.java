package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CurrencyRate;

@Repository
public class CurrencyRateDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("deprecation")
    public CurrencyRate getCurrencyRate(BigInteger toCurrencyId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(CurrencyRate.class);
        crit.add(Restrictions.eq("baseCurrencyId", toCurrencyId));
        return (CurrencyRate) crit.uniqueResult();
    }

    public CurrencyRate getCurrencyRate(String toCurrencyCode) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(CurrencyRate.class);
        crit.add(Restrictions.eq("toCurrencyCode", toCurrencyCode));
        return (CurrencyRate) crit.uniqueResult();
    }
    
    public void save(CurrencyRate currencyRate) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(currencyRate);
    }

	public List<CurrencyRate> getAllCurrencyRate() {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(CurrencyRate.class);
        return (List<CurrencyRate>) crit.list();
	}

	public List<CurrencyRate> getChangedCurrency() {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(CurrencyRate.class);
        crit.add(Restrictions.eq("hasChanged", true));
        return (List<CurrencyRate>) crit.list();
	}
}
