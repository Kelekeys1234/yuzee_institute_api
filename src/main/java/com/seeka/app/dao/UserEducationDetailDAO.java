package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.GradeDetails;
import com.seeka.app.bean.UserEducationDetails;

@Repository
@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
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
        UserEducationDetails educationDetails = null;
        if (id != null) {
            Session session = sessionFactory.getCurrentSession();
            educationDetails = session.get(UserEducationDetails.class, id);
        }
        return educationDetails;
    }

    @Override
    public UserEducationDetails getUserEducationDetails(BigInteger userId) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "SELECT ed.user_id, ed.edu_country,c.name as CountryName, ed.edu_system_id,"
                        + "s.name as SystemName, ed.edu_institue, ed.gpa_score, ed.is_english_medium,"
                        + "ed.english_level, ed.edu_sys_score, ed.edu_level, ed.id, ed.is_active, ed.created_on, ed.created_by FROM user_education_details ed "
                        + "left join education_system s on s.id = ed.edu_system_id left join " + "country c on c.id=ed.edu_country where ed.is_active = 1 and ed.user_id = '"
                        + userId + "'";
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        UserEducationDetails userEducationDetails = null;
        for (Object[] row : rows) {
            userEducationDetails = new UserEducationDetails();
            userEducationDetails.setUserId(new BigInteger(row[0].toString()));
            userEducationDetails.setEduCountry(new BigInteger(row[1].toString()));
            userEducationDetails.setEducationCountryName(row[2].toString());
            userEducationDetails.setEduSystemId(new BigInteger(row[3].toString()));
            userEducationDetails.setEducationSystemName(row[4].toString());
            userEducationDetails.setEduInstitue(row[5].toString());
            if (null != row[6]) {
                userEducationDetails.setGpaScore(row[6].toString());
            }
            if (null != row[7]) {
                userEducationDetails.setIsEnglishMedium(row[7].toString());
            }
            if (null != row[8]) {
                userEducationDetails.setEnglishLevel(row[8].toString());
            }
            if (null != row[9]) {
                userEducationDetails.setEduSysScore(row[9].toString());
            }
            if (null != row[10]) {
                userEducationDetails.setEduLevel(row[10].toString());
            }
            userEducationDetails.setId(new BigInteger(row[11].toString()));
            if (row[12] != null) {
                if (row[12].toString().equals("1")) {
                    userEducationDetails.setIsActive(true);
                } else {
                    userEducationDetails.setIsActive(false);
                }
            }
            if (row[13] != null) {
                Date createdDate = (Date) row[13];
                System.out.println(createdDate);
                userEducationDetails.setCreatedOn(createdDate);
            }
            if (row[14] != null) {
                userEducationDetails.setCreatedBy(row[14].toString());
            }
        }
        return userEducationDetails;
    }

    public String getGradeDetails(BigInteger countryId, BigInteger educationSystemId, String grade) {
        String gpaGrade = "0.0";
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(GradeDetails.class);
        crit.add(Restrictions.eq("countryId", countryId)).add(Restrictions.eq("educationSystemId", educationSystemId)).add(Restrictions.eq("grade", grade));
        List<GradeDetails> details = crit.list();
        ArrayList<GradeDetails> min = new ArrayList<GradeDetails>();
        for (GradeDetails x : details) {
            if (min.size() == 0 || Double.valueOf(x.getGpaGrade()) == Double.valueOf(min.get(0).getGpaGrade()))
                min.add(x);
            else if (Double.valueOf(x.getGpaGrade()) < Double.valueOf(min.get(0).getGpaGrade())) {
                min.clear();
                min.add(x);
            }
        }
        if (min != null && !min.isEmpty()) {
            gpaGrade = min.get(0).getGpaGrade();
        }
        return gpaGrade;
    }
}