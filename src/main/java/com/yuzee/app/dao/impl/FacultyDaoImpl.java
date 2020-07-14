package com.yuzee.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.Faculty;
import com.yuzee.app.dao.FacultyDao;

@Component
@SuppressWarnings({ "unchecked", "deprecation" })
public class FacultyDaoImpl implements FacultyDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdateFaculty(final Faculty obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
	}

	@Override
	public Faculty get(final String id) {
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
	public List<Faculty> getFacultyListByFacultyNames(final List<String> facultyNameList) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Faculty.class, "faculty");
		crit.add(Restrictions.in("name", facultyNameList));
		return crit.list();
	}

	@Override
	public Faculty getFacultyByFacultyName(String facultyName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Faculty.class, "faculty");
		crit.add(Restrictions.in("name", facultyName));
		Faculty faculty = (Faculty) crit.uniqueResult();
		return faculty;
	}
}
