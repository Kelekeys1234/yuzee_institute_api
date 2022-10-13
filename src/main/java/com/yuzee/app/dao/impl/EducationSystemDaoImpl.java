package com.yuzee.app.dao.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.bean.GradeDetails;
import com.yuzee.app.bean.Subject;
import com.yuzee.app.dao.EducationSystemDao;
import com.yuzee.app.dto.CourseDto;
import com.yuzee.app.repository.EducationSystemRepository;
import com.yuzee.common.lib.dto.institute.EducationSystemDto;
import com.yuzee.common.lib.dto.institute.GradeDto;
import com.yuzee.common.lib.dto.institute.SubjectDto;
import com.yuzee.common.lib.enumeration.GradeType;

@Component
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
public class EducationSystemDaoImpl implements EducationSystemDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private EducationSystemRepository educationSystemRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public EducationSystem save(final EducationSystem hobbiesObj) {
		return educationSystemRepository.save(hobbiesObj);
	}

	public void saveAll(List<EducationSystem> educationSystems) {
		educationSystemRepository.saveAll(educationSystems);
	}

	@Override
	public void update(final EducationSystem hobbiesObj) {
		educationSystemRepository.save(hobbiesObj);
	}

	@Transactional
	@Override
	public Optional<EducationSystem> get(final String id) {
		return educationSystemRepository.findById(id);
	}

	@Override
	public List<EducationSystem> getAll() {
//		Session session = sessionFactory.getCurrentSession();
//		Criteria crit = session.createCriteria(EducationSystem.class);
//		return crit.list();
		return educationSystemRepository.findAll();
	}

	@Override
	public List<EducationSystem> getAllGlobeEducationSystems() {
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createSQLQuery(
//				"select id,name,code,country_id,is_active from education_system  where country_id is null "
//						+ "and is_active = 1 order by code");
//		List<Object[]> rows = query.list();
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("countryName").is(null));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("is_active").is(1));
		
		query.with(Sort.by(Sort.Direction.ASC, "code"));
		List<EducationSystem> rows = mongoTemplate.find(query,EducationSystem.class,"education_system");
		List<EducationSystem> list = new ArrayList<>();
		for (EducationSystem row : rows) {
			EducationSystem obj = new EducationSystem();
			// obj.setId(row[0].toString());
			obj.setName(row.getName());
			obj.setCode(row.getCode());
			list.add(obj);
		}
		return list;
	}

	@Override
	public List<EducationSystem> getEducationSystemsByCountryName(final String countryId) {
//		Session session = sessionFactory.getCurrentSession();
//		Criteria criteria = session.createCriteria(EducationSystem.class, "educationSystem");
//		criteria.add(Restrictions.eq("educationSystem.countryName", countryId));
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("countryName").is(countryId));
		return mongoTemplate.find(query, EducationSystem.class,"education_system");
		
	}

	@Override
	public List<Subject> getSubject() {
		Query query = new Query();
		return mongoTemplate.find(query, Subject.class,"subject");
		

	}

	@Override
	public List<Subject> getSubjectByEducationSystem(final String educationSystemId) {
//		Session session = sessionFactory.getCurrentSession();
//		Criteria crit = session.createCriteria(Subject.class);
//		crit.add(Restrictions.eq("educationSystemId", educationSystemId));
		//return crit.list();
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("educationSystemId").is(educationSystemId));
		return mongoTemplate.find(query, Subject.class,"subject");
		

	}

	@Override
	public List<EducationSystemDto> getEducationSystemByCountryNameAndStateName(String countryName, String stateName) {
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createSQLQuery(
//				"select es.id,es.country_name,es.name,es.code,es.description,es.state_name,es.grade_type from education_system es where es.country_name='"
//						+ countryName + "'and( es.state_name='" + stateName + "' OR es.state_name = 'All' )");
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("countryName").is(countryName));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("stateName").is(stateName));
		
		List<EducationSystem> rows = mongoTemplate.find(query, EducationSystem.class,"education_system");
		List<EducationSystemDto> list = new ArrayList<>();
		List<SubjectDto> subjects = new ArrayList<>();
		List<GradeDto> gradeDetails = new ArrayList<>();
		for (EducationSystem row : rows) {
			EducationSystemDto obj = new EducationSystemDto();
			obj.setId(row.getId());
			obj.setCountryName(row.getCountryName());
			obj.setName(row.getName());
			obj.setCode(row.getCode());
			obj.setDescription(row.getDescription());
			obj.setStateName(row.getStateName());
			obj.setGradeType(GradeType.valueOf(row.getGradeType().toString()));

//			Query subjectQuery = session.createSQLQuery(
//					"select id,subject_name from subject where education_system_id ='" + row[0].toString() + "'");
		
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("educationSystem.id").is(row.getId()));
			List<Subject> subjectRows=    mongoTemplate.find(query, Subject.class,"subject");
			for (Subject subjectRow : subjectRows) {
				SubjectDto subject = new SubjectDto();
				subject.setId(subjectRow.getId());
				subject.setSubjectName(subjectRow.getName());
				subjects.add(subject);
				obj.setSubjects(subjects);
			}

//			Query gradeQuery = session.createSQLQuery(
//					"SELECT DISTINCT id,country_name,grade,MIN(gpa_grade) FROM grade_details where education_system_id ='"
//							+ row[0].toString() + "' GROUP BY grade;");
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("educationSystem.id").is(row.getId()));
			List<GradeDetails> gradeRows=    mongoTemplate.find(query,GradeDetails.class,"grade");
			for (GradeDetails gradeRow : gradeRows) {
				GradeDto grade = new GradeDto();
				grade.setId(gradeRow.getId());
				grade.setCountryName(gradeRow.getCountryName());
				if (gradeRow.getGrade() != null) {
					grade.setGrade(gradeRow.getGrade());
				}
				if (gradeRow.getGpaGrade().toString() != null) {
					grade.setGpaGrade(gradeRow.getGpaGrade());
				}
				gradeDetails.add(grade);
				obj.setGradeDtos(gradeDetails);
			}
			list.add(obj);
		}
		return list;
	}

	@Override
	public EducationSystem findByNameAndCountryNameAndStateName(String name, String countryName, String stateName) {
		return educationSystemRepository.findByNameAndCountryNameAndStateName(name, countryName, stateName);
	}

}
