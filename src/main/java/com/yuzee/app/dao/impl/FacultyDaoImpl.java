package com.yuzee.app.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.Faculty;
import com.yuzee.app.dao.FacultyDao;
import com.yuzee.app.dto.FacultyDto;

@Component
@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
public class FacultyDaoImpl implements FacultyDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final Faculty obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public void update(final Faculty obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
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
	public List<Faculty> getFacultyByCountryIdAndLevelId(final String countryID, final String levelId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select distinct f.id, f.name as facultyName, il.level_id as levelid from institute_level il inner join faculty_level fl on fl.institute_id = il.institute_id inner join faculty f on fl.faculty_id= f.id where il.country_name = :countryId and il.level_id = :levelId")
				.setParameter("countryId", countryID).setParameter("levelId", levelId);
		List<Object[]> rows = query.list();
		List<Faculty> faculties = new ArrayList<>();
		Faculty obj = null;
		for (Object[] row : rows) {
			obj = new Faculty();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			faculties.add(obj);
		}
		return faculties;
	}

	@Override
	public List<FacultyDto> getAllFacultyByCountryIdAndLevel() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select distinct f.id, f.name as facultyName,f.level_id as levelid,il.country_name from faculty f  "
						+ "inner join institute_level il  on il.level_id = f.level_id");
		List<Object[]> rows = query.list();
		List<FacultyDto> faculties = new ArrayList<>();
		for (Object[] row : rows) {
			FacultyDto facultyDto = new FacultyDto();
			facultyDto.setId(row[0].toString());
			facultyDto.setName(row[1].toString());
			faculties.add(facultyDto);
		}
		return faculties;

	}

	@Override
	public List<FacultyDto> getFacultyByInstituteId(final String instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select distinct f.id, f.name as facultyName,f.level_id as levelid,f.description as description from faculty f  "
						+ "inner join faculty_level fl  on f.id = fl.faculty_id where fl.institute_id = '" + instituteId
						+ "' ORDER BY f.name");
		List<Object[]> rows = query.list();
		List<FacultyDto> faculties = new ArrayList<>();
		for (Object[] row : rows) {
			FacultyDto facultyDto = new FacultyDto();
			facultyDto.setId(row[0].toString());
			facultyDto.setName(row[1].toString());
			facultyDto.setDescription(row[3].toString());
			faculties.add(facultyDto);
		}
		return faculties;
	}

	@Override
	public List<FacultyDto> getFacultyByListOfInstituteId(final String instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select distinct f.id, f.name as facultyName,f.level_id as levelid,f.description as description from faculty f  "
						+ "inner join faculty_level fl  on f.id = fl.faculty_id where fl.institute_id in ("
						+ instituteId + ") ORDER BY f.name");
		List<Object[]> rows = query.list();
		List<FacultyDto> faculties = new ArrayList<>();
		for (Object[] row : rows) {
			FacultyDto facultyDto = new FacultyDto();
			facultyDto.setId(row[0].toString());
			facultyDto.setName(row[1].toString());
			if (row[3] != null) {
				facultyDto.setDescription(row[3].toString());
			}
			faculties.add(facultyDto);
		}
		return faculties;
	}

	@Override
	public List<Faculty> getFacultyListByFacultyNames(final List<String> facultyNameList) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Faculty.class, "faculty");
		crit.add(Restrictions.in("name", facultyNameList));
		return crit.list();
	}

	@Override
	public List<FacultyDto> getCourseFaculty(final String countryId, final String levelId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select distinct f.id, f.name as facultyName, il.level_id as levelid from course c inner join institute_level il on c.institute_id = il.institute_id inner join faculty f on c.faculty_id= f.id where il.country_name = :countryId and il.level_id = :levelId")
				.setParameter("countryId", countryId).setParameter("levelId", levelId);
		List<Object[]> rows = query.list();
		List<FacultyDto> faculties = new ArrayList<>();
		FacultyDto obj = null;
		for (Object[] row : rows) {
			obj = new FacultyDto();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			faculties.add(obj);
		}
		return faculties;
	}

	@Override
	public Faculty getFacultyByFacultyName(String facultyName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Faculty.class, "faculty");
		crit.add(Restrictions.in("name", facultyName));
		Faculty faculty = (Faculty) crit.uniqueResult();
		return faculty;
	}

	@Override
	public List<String> getAllFacultyNamesByInstituteId(final String instituteId) {
		List<String> list = new ArrayList<String>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select f.id,f.`name`"
				+ "from faculty_level fl left join faculty f on f.id = fl.faculty_id where fl.institute_id='"
				+ instituteId + "'");
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			list.add(new String(row[1].toString()));
		}
		return list;
	}

}
