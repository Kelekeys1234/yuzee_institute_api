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

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.bean.Subject;

@Repository
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
public class EducationSystemDAO implements IEducationSystemDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final EducationSystem hobbiesObj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(hobbiesObj);
	}

	@Override
	public void update(final EducationSystem hobbiesObj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(hobbiesObj);
	}

	@Override
	public EducationSystem get(final String id) {
		Session session = sessionFactory.getCurrentSession();
		EducationSystem obj = session.get(EducationSystem.class, id);
		return obj;
	}

	@Override
	public List<EducationSystem> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(EducationSystem.class);
		return crit.list();
	}

	@Override
	public List<EducationSystem> getAllGlobeEducationSystems() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select id,name,code,country_id,is_active from education_system  where country_id is null " + "and is_active = 1 order by code");
		List<Object[]> rows = query.list();
		List<EducationSystem> list = new ArrayList<>();
		for (Object[] row : rows) {
			EducationSystem obj = new EducationSystem();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			obj.setCode(row[2].toString());
			list.add(obj);
		}
		return list;
	}

	@Override
	public List<EducationSystem> getEducationSystemsByCountryId(final String countryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EducationSystem.class, "educationSystem");
		criteria.createAlias("educationSystem.country", "country");
		criteria.add(Restrictions.eq("country.id", countryId));
		/*
		 *
		 * Query query = session
		 * .createSQLQuery("select id, name, code ,country_id ,is_active from education_system  where country_id="
		 * + countryId + " and is_active = 1 order by code"); List<Object[]> rows =
		 * query.list(); List<EducationSystem> list = new ArrayList<>(); for (Object[]
		 * row : rows) { EducationSystem obj = new EducationSystem(); obj.setId(new
		 * BigInteger((row[0].toString()))); obj.setName(row[1].toString());
		 * obj.setCode(row[2].toString()); obj.setSubjects(getSubject(session));
		 * list.add(obj); }
		 */
		return criteria.list();
	}

	@Override
	public List<Subject> getSubject() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Subject.class);
		return crit.list();
	}

	@Override
	public List<Subject> getSubjectByEducationSystem(final BigInteger educationSystemId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Subject.class);
		crit.add(Restrictions.eq("educationSystemId", educationSystemId));
		return crit.list();
	}
}