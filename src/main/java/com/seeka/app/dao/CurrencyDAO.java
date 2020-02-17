package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Currency;
import com.seeka.app.bean.CurrencyRate;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })

public class CurrencyDAO implements ICurrencyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Currency obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    @Override
    public void update(Currency obj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }

    @Override
    public Currency get(String id) {
        Session session = sessionFactory.getCurrentSession();
        Currency obj = session.get(Currency.class, id);
        return obj;
    }

    @Override
    public List<Currency> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Currency.class);
        return crit.list();
    }

    @Override
    public List<Currency> getCourseTypeByCountryId(String countryID) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select distinct ct.id, ct.type_txt as courseType from course_type ct  inner join faculty f  on f.course_type_id = ct.id "
                        + "inner join course c  on c.faculty_id = f.id inner join institute_course ic  on ic.course_id = c.id " + "where ic.country_id = :countryId")
                        .setParameter("countryId", countryID);
        List<Object[]> rows = query.list();
        List<Currency> courseTypes = new ArrayList<>();
        for (Object[] row : rows) {
            Currency obj = new Currency();
            obj.setId((row[0].toString()));
            obj.setName(row[1].toString());
            courseTypes.add(obj);
        }
        return courseTypes;
    }

    @Override
    public List<Currency> getCurrencyByCountryId(String countryId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session
                        .createSQLQuery("select distinct le.id, le.name as name,le.Currency_key as Currencykey from Currency le  inner join institute_Currency il  on il.Currency_id = le.id "
                                        + "inner join country c  on c.id = il.country_id " + "where il.country_id = :countryId")
                        .setParameter("countryId", countryId);

        List<Object[]> rows = query.list();

        List<Currency> Currency = new ArrayList<>();

        for (Object[] row : rows) {
            Currency obj = new Currency();
            obj.setId(row[0].toString());
            obj.setName(row[1].toString());
            Currency.add(obj);
        }
        return Currency;
    }

    @Override
    public Currency getCurrencyByCode(String currencyCode) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Currency.class);
        crit.add(Restrictions.eq("code", currencyCode));
        return (Currency) crit.list().get(0);
    }

    public CurrencyRate getCurrencyRate(String toCurrencyCode) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(CurrencyRate.class);
        crit.add(Restrictions.eq("toCurrencyCode", toCurrencyCode));
        return (CurrencyRate) crit.uniqueResult();
    }

//    public CurrencyRate saveCurrencyRate(BigInteger fromCurrencyId, String courseCurrency) {
//        Session session = sessionFactory.getCurrentSession();
//        CurrencyRate currencyRate = null;
//        String currencyResponse = CommonUtil.getCurrencyDetails(courseCurrency);
//        if (currencyResponse != null) {
//            currencyRate = new CurrencyRate();
//            currencyRate.setCreatedDate(DateUtil.getUTCdatetimeAsStringYYYY_MM_DD());
//            currencyRate.setBaseCurrencyId(fromCurrencyId);
//            currencyRate.setRateDetail(currencyResponse);
//            session.save(currencyRate);
//        }
//        return currencyRate;
//    }

//    public Double getConvertedCurrency(CurrencyRate currencyRate, BigInteger toCurrencyId, Double amount) {
//        Session session = sessionFactory.getCurrentSession();
//        Double convertedRate = null;
//        if (currencyRate.getRateDetail() != null) {
//            Currency currency = getCurrency(toCurrencyId, session);
//            JSONParser parser = new JSONParser();
//            org.json.simple.JSONObject json;
//            try {
//                json = (org.json.simple.JSONObject) parser.parse(currencyRate.getRateDetail());
//                org.json.simple.JSONObject rates = (org.json.simple.JSONObject) json.get("rates");
//                Double currencyValue = null;
//                for (Iterator iterator = rates.keySet().iterator(); iterator.hasNext();) {
//                    String key = (String) iterator.next();
//                    if (key.equalsIgnoreCase(currency.getCode())) {
//                        if (rates.get(key) != null && String.valueOf(rates.get(key)).contains(".")) {
//                            currencyValue = (Double) rates.get(key);
//                        } else {
//                            currencyValue = Double.valueOf((Long) rates.get(key));
//                        }
//                        break;
//                    }
//                }
//                if (currencyValue != null) {
//                    convertedRate = currencyValue * amount;
//                }
//            } catch (org.json.simple.parser.ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        return convertedRate;
//    }

    private Currency getCurrency(String id, Session session) {
        Currency obj = session.get(Currency.class, id);
        return obj;
    }
}
