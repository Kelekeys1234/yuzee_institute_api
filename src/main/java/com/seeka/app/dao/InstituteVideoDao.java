package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteVideos;
import com.seeka.app.dto.InstituteMedia;

@SuppressWarnings({ "deprecation", "unchecked" })
@Repository
public class InstituteVideoDao implements IInstituteVideoDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(InstituteVideos instituteVideo) {
        Session session = sessionFactory.getCurrentSession();
        session.save(instituteVideo);
    }

    @Override
    public List<InstituteMedia> findByInstituteId(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(InstituteMedia.class);
        crit.add(Restrictions.eq("institute.id", id));
        return crit.list();
    }
}
