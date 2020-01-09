package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.City;

@Repository
@SuppressWarnings("unchecked")
public class CityDAO implements ICityDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(City obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    @Override
    public List<City> getAll() {
        List<City> citis = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select c.id, c.name as name from city c  ORDER BY c.name");
        List<Object[]> rows = query.list();
        City obj = null;
        for (Object[] row : rows) {
            obj = new City();
            obj.setId(new BigInteger(row[0].toString()));
            obj.setName(row[1].toString());
            citis.add(obj);
        }
        return citis;
    }

    @Override
    public City get(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        City city = session.get(City.class, id);
        return city;
    }

    public List<City> getAllCitiesByCountry(BigInteger countryId) {
        List<City> citis = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select c.id, c.name as name from city c  where c.country_id = " + countryId + " ORDER BY c.name");
        List<Object[]> rows = query.list();
        City obj = null;
        for (Object[] row : rows) {
            obj = new City();
            obj.setId(new BigInteger(row[0].toString()));
            obj.setName(row[1].toString());
            citis.add(obj);
        }
        return citis;
    }

    @Override
    public List<City> getAllMultipleCitiesByCountry(String BigIntegers) {
        List<City> citis = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select c.id, c.name as name from city c  where c.country_id in (" + BigIntegers + ") ORDER BY c.name");
        List<Object[]> rows = query.list();
        City obj = null;
        for (Object[] row : rows) {
            obj = new City();
            obj.setId(new BigInteger(row[0].toString()));
            obj.setName(row[1].toString());
            citis.add(obj);
        }
        City allObj = new City();
        allObj.setId(new BigInteger("111111"));
        allObj.setName("All");
        citis.add(allObj);
        return citis;
    }
}
