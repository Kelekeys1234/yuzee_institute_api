package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Faculty;

@Repository
@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
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
    public Faculty get(BigInteger id) {
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
    public List<Faculty> getFacultyByCountryIdAndLevelId(BigInteger countryID, BigInteger levelId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(
                        "select distinct f.id, f.name as facultyName, il.level_id as levelid from institute_level il inner join faculty_level fl on fl.institute_id = il.institute_id inner join faculty f on fl.faculty_id= f.id where il.country_id = :countryId and il.level_id = :levelId")
                        .setParameter("countryId", countryID).setParameter("levelId", levelId);
        List<Object[]> rows = query.list();
        List<Faculty> faculties = new ArrayList<Faculty>();
        Faculty obj = null;
        for (Object[] row : rows) {
            obj = new Faculty();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setName(row[1].toString());
            faculties.add(obj);
        }
        return faculties;
    }

    @Override
    public List<Faculty> getAllFacultyByCountryIdAndLevel() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select distinct f.id, f.name as facultyName,f.level_id as levelid,il.country_id from faculty f  "
                        + "inner join institute_level il  on il.level_id = f.level_id");
        List<Object[]> rows = query.list();
        List<Faculty> faculties = new ArrayList<Faculty>();
        Faculty obj = null;
        for (Object[] row : rows) {
            obj = new Faculty();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setName(row[1].toString());
            obj.setLevelId(new BigInteger((row[2].toString())));
            obj.setCountryId(new BigInteger((row[3].toString())));
            faculties.add(obj);
        }
        return faculties;

    }

    @Override
    public List<Faculty> getFacultyByInstituteId(BigInteger instituteId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select distinct f.id, f.name as facultyName,f.level_id as levelid,f.description as description from faculty f  "
                        + "inner join faculty_level fl  on f.id = fl.faculty_id where fl.institute_id = '" + instituteId + "' ORDER BY f.name");
        List<Object[]> rows = query.list();
        List<Faculty> faculties = new ArrayList<Faculty>();
        Faculty obj = null;
        for (Object[] row : rows) {
            obj = new Faculty();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setName(row[1].toString());
            obj.setLevelId(new BigInteger((row[2].toString())));
            obj.setDescription(row[3].toString());
            faculties.add(obj);
        }
        return faculties;
    }

    @Override
    public List<Faculty> getFacultyByListOfInstituteId(String instituteId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select distinct f.id, f.name as facultyName,f.level_id as levelid,f.description as description from faculty f  "
                        + "inner join faculty_level fl  on f.id = fl.faculty_id where fl.institute_id in (" + instituteId + ") ORDER BY f.name");
        List<Object[]> rows = query.list();
        List<Faculty> faculties = new ArrayList<Faculty>();
        Faculty obj = null;
        for (Object[] row : rows) {
            obj = new Faculty();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setName(row[1].toString());
            obj.setLevelId(new BigInteger((row[2].toString())));
            if (row[3] != null) {
                obj.setDescription(row[3].toString());
            }
            faculties.add(obj);
        }
        Faculty allObject = new Faculty();
        allObject.setId(new BigInteger("111111"));
        allObject.setName("All");
        faculties.add(allObject);
        return faculties;
    }
    
    public List<Faculty> getFacultyListByFacultyNames(List<String> facultyNameList){
    	Session session = sessionFactory.getCurrentSession();
    	Criteria crit = session.createCriteria(Faculty.class, "faculty");
		crit.add(Restrictions.in("name", facultyNameList));
		return crit.list();
    }


    @Override
    public List<Faculty> getCourseFaculty(BigInteger countryId, BigInteger levelId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(
                        "select distinct f.id, f.name as facultyName, il.level_id as levelid from course c inner join institute_level il on c.institute_id = il.institute_id inner join faculty f on c.faculty_id= f.id where il.country_id = :countryId and il.level_id = :levelId")
                        .setParameter("countryId", countryId).setParameter("levelId", levelId);
        List<Object[]> rows = query.list();
        List<Faculty> faculties = new ArrayList<Faculty>();
        Faculty obj = null;
        for (Object[] row : rows) {
            obj = new Faculty();
            obj.setId(new BigInteger((row[0].toString())));
            obj.setName(row[1].toString());
            faculties.add(obj);
        }
        return faculties;
    }
}
