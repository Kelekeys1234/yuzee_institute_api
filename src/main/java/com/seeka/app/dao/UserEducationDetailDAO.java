package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserEducationDetails;

@Repository
public class UserEducationDetailDAO implements IUserEducationDetailDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(UserEducationDetails hobbiesObj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(hobbiesObj);
    }

    @Override
    public void update(UserEducationDetails hobbiesObj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(hobbiesObj);
    }

    @Override
    public UserEducationDetails get(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        UserEducationDetails obj = session.get(UserEducationDetails.class, id);
        return obj;
    }

    @Override
    public UserEducationDetails getUserEducationDetails(BigInteger userId) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "SELECT user_id, edu_country,c.name as CountryName, edu_system_id," + "s.name as SystemName, edu_institue, gpa_score, is_english_medium,"
                        + "english_level, edu_sys_score, edu_level FROM user_education_details ed " + "inner join education_system s on s.id = ed.edu_system_id inner join "
                        + "country c on c.id=ed.edu_country where ed.is_active = 1 and ed.user_id = '" + userId + "'";
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        UserEducationDetails obj = null;
        for (Object[] row : rows) {
            obj = new UserEducationDetails();
            obj.setUserId(new BigInteger(row[0].toString()));
            obj.setEduCountry(new BigInteger(row[1].toString()));
            obj.setEducationCountryName(row[2].toString());
            obj.setEduSystemId(new BigInteger(row[3].toString()));
            obj.setEducationSystemName(row[4].toString());
            obj.setEduInstitue(row[5].toString());
            if (null != row[6]) {
                obj.setGpaScore(row[6].toString());
            }
            if (null != row[7]) {
                obj.setIsEnglishMedium(row[7].toString());
            }
            if (null != row[8]) {
                obj.setEnglishLevel(row[8].toString());
            }
            if (null != row[9]) {
                obj.setEduSysScore(row[9].toString());
            }
            if (null != row[10]) {
                obj.setEduLevel(row[10].toString());
            }
        }
        return obj;
    }
}