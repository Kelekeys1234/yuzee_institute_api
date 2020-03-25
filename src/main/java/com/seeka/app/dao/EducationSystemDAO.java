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

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.bean.Subject;
import com.seeka.app.dto.EducationSystemDto;
import com.seeka.app.dto.SubjectDto;

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
		return criteria.list();
	}

	@Override
	public List<Subject> getSubject() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Subject.class);
		return crit.list();
	}

	@Override
	public List<Subject> getSubjectByEducationSystem(final String educationSystemId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Subject.class);
		crit.add(Restrictions.eq("educationSystemId", educationSystemId));
		return crit.list();
	}

	@Override
	public List<EducationSystemDto> getEducationSystemByCountryNameAndStateName(String countryName, String stateName) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select es.id,es.country_name,es.name,es.code,es.description,es.state_name from education_system es where es.country_name='"+countryName+"'and es.state_name='"+stateName+"'");
		List<Object[]> rows = query.list();
		List<EducationSystemDto> list = new ArrayList<>();
		List<SubjectDto> subjects = new ArrayList<>();
		for (Object[] row : rows) {
			EducationSystemDto obj = new EducationSystemDto();
			obj.setId(row[0].toString());
			obj.setCountryName(row[1].toString());
			obj.setName(row[2].toString());
			obj.setCode(row[3].toString());
			obj.setDescription(row[4].toString());
			obj.setStateName(row[5].toString());
			
			Query subjectQuery = session.createSQLQuery("select id,subject_name from seeka_subject where education_system_id ='"+row[0].toString()+"'");
			List<Object[]> stateRows = subjectQuery.list();
			for (Object[] stateRow : stateRows) {
				SubjectDto subject = new SubjectDto();
				subject.setId(stateRow[0].toString());
				subject.setSubjectName(stateRow[1].toString());
				subjects.add(subject);
				obj.setSubjects(subjects);
			}
			list.add(obj);
		}
		return list;
	}
}