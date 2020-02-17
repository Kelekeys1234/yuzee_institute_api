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

import com.seeka.app.bean.FacultyLevel;
import com.seeka.app.bean.Institute;

@Repository
public class FacultyLevelDAO implements IFacultyLevelDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final FacultyLevel obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public void update(final FacultyLevel obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
	}

	@Override
	public FacultyLevel get(final String id) {
		Session session = sessionFactory.getCurrentSession();
		FacultyLevel obj = session.get(FacultyLevel.class, id);
		return obj;
	}

	@Override
	public List<FacultyLevel> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Institute.class);
		return crit.list();
	}

	@Override
	public List<FacultyLevel> getFacultyByCountryIdAndCourseTypeId(final String countryID, final String courseTypeId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("select distinct f.id, f.name as facultyName from faculty f  "
						+ "inner join course c  on c.faculty_id = f.id inner join institute_course ic  on ic.course_id = c.id "
						+ "where ic.country_id = :countryId and f.course_type_id = :courseTypeId")
				.setParameter("countryId", countryID).setParameter("courseTypeId", courseTypeId);
		List<Object[]> rows = query.list();
		List<FacultyLevel> faculties = new ArrayList<>();
		FacultyLevel obj = null;
		for (Object[] row : rows) {
			obj = new FacultyLevel();
			obj.setId(row[0].toString());
			/* obj.setName(row[1].toString()); */
			faculties.add(obj);
		}
		return faculties;
	}

	@Override
	public List<FacultyLevel> getAllFacultyLevelByInstituteId(final String instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(FacultyLevel.class, "facultyLevel");
		crit.createAlias("facultyLevel.institute", "institute");
		crit.add(Restrictions.eq("institute.id", instituteId));
		return crit.list();
	}

	@Override
	public void deleteFacultyLevel(final String instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("DELETE FROM faculty_level WHERE institute_id =" + instituteId);
		query.executeUpdate();
	}

}
