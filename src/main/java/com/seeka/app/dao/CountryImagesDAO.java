package com.seeka.app.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CityImages;
import com.seeka.app.bean.CountryImages;

@Repository
public class CountryImagesDAO implements ICountryImagesDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<CountryImages> getAll() {
        System.out.println("Inside Country DAO....");
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        List<CountryImages> list = session.createCriteria(CountryImages.class).list();
        return list;
    }

    public void save(CityImages cityImages) {
        Session session = sessionFactory.getCurrentSession();
        session.save(cityImages);
    }
}
