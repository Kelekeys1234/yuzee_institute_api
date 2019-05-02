package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Faculty;

@Repository
@SuppressWarnings("unchecked")
public class FacultyDAO implements IFacultyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Faculty obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    @Override
    public void update(Faculty obj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }

    @Override
    public Faculty get(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        Faculty obj = session.get(Faculty.class, id);
        return obj;
    }

    @Override
    public List<Faculty> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Faculty.class);
        return crit.list();
    }

    @Override
    public List<Faculty> getFacultyByCountryIdAndLevelId(UUID countryID, UUID levelId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session
                        .createSQLQuery("select distinct f.id, f.name as facultyName,f.level_id as levelid from faculty f with(nolock) "
                                        + "inner join institute_level il with(nolock) on il.level_id = f.level_id " + "where il.country_id = :countryId and f.level_id = :levelId")
                        .setParameter("countryId", countryID).setParameter("levelId", levelId);
        List<Object[]> rows = query.list();
        List<Faculty> faculties = new ArrayList<Faculty>();
        Faculty obj = null;
        for (Object[] row : rows) {
            obj = new Faculty();
            obj.setId(UUID.fromString((row[0].toString())));
            obj.setName(row[1].toString());
            faculties.add(obj);
        }
        return faculties;
    }

    @Override
    public List<Faculty> getAllFacultyByCountryIdAndLevel() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select distinct f.id, f.name as facultyName,f.level_id as levelid,il.country_id from faculty f with(nolock) "
                        + "inner join institute_level il with(nolock) on il.level_id = f.level_id");
        List<Object[]> rows = query.list();
        List<Faculty> faculties = new ArrayList<Faculty>();
        Faculty obj = null;
        for (Object[] row : rows) {
            obj = new Faculty();
            obj.setId(UUID.fromString((row[0].toString())));
            obj.setName(row[1].toString());
            obj.setLevelId(UUID.fromString((row[2].toString())));
            obj.setCountryId(UUID.fromString((row[3].toString())));
            faculties.add(obj);
        }
        return faculties;

    }

    @Override
    public List<Faculty> getFacultyByInstituteId(UUID instituteId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session
                        .createSQLQuery("select distinct f.id, f.name as facultyName,f.level_id as levelid,f.description as description from faculty f with(nolock) "
                                        + "inner join faculty_level fl with(nolock) on f.id = fl.faculty_id where fl.institute_id = '" + instituteId + "'");
        List<Object[]> rows = query.list();
        List<Faculty> faculties = new ArrayList<Faculty>();
        Faculty obj = null;
        for (Object[] row : rows) {
            obj = new Faculty();
            obj.setId(UUID.fromString((row[0].toString())));
            obj.setName(row[1].toString());
            obj.setLevelId(UUID.fromString((row[2].toString())));
            obj.setDescription(row[3].toString());
            faculties.add(obj);
        }
        return faculties;
    }
}
