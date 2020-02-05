package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.YoutubeVideo;

@Repository("youtubeVideoDAO")
@Transactional
@SuppressWarnings({ "deprecation", "unchecked" })
public class YoutubeVideoDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<YoutubeVideo> getAllYoutubeVideo() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(YoutubeVideo.class).list();
    }

    public List<YoutubeVideo> getYoutubeVideoByCountryId(String id, String countryType) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(YoutubeVideo.class);
        crit.add(Restrictions.eq("id", id)).add(Restrictions.eq("type", countryType));
        return crit.list();
    }
}
