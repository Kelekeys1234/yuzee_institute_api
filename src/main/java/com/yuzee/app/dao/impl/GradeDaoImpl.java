package com.yuzee.app.dao.impl;

import static org.hamcrest.CoreMatchers.is;


import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.bean.GradeDetails;
import com.yuzee.app.dao.GradeDao;
import com.yuzee.app.repository.GradeDetailRepository;

@Component
public class GradeDaoImpl implements GradeDao {

	@Autowired
 	private MongoTemplate mongoTemplate;
	@Autowired
	private GradeDetailRepository gradeDetailRepository;
	
	@Override
	public String getGradeDetails(String countryId, String educationSystemId, String grade) {
		String gpaGrade = "0.0";
		Query query = new Query();
		if(countryId!=null) {
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("countryName").is(countryId));
		} 
		if(educationSystemId!=null) {
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("educationSystemId").is(educationSystemId));
			}
		List<GradeDetails>details= mongoTemplate.find(query,GradeDetails.class,"grade");
		ArrayList<GradeDetails> min = new ArrayList<>();
		for (GradeDetails x : details) {
			if (min.size() == 0 || Double.valueOf(x.getGpaGrade()) == Double.valueOf(min.get(0).getGpaGrade())) {
				min.add(x);
			} else if (Double.valueOf(x.getGpaGrade()) < Double.valueOf(min.get(0).getGpaGrade())) {
				min.clear();
				min.add(x);
			}
		}
		if (min != null && !min.isEmpty()) {
			gpaGrade = min.get(0).getGpaGrade();
		}
		return gpaGrade;
	}

	@Override
	public List<GradeDetails> getGrades(String countryId, String systemId) {
//		Session session = sessionFactory.getCurrentSession();
//		Criteria crit = session.createCriteria(GradeDetails.class);
//		crit.add(Restrictions.eq("countryName", countryId));
//		crit.add(Restrictions.eq("educationSystemId", systemId));
		Query query = new Query();
		if(countryId!=null) {
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("countryName").is(countryId));
		} 
		if(systemId!=null) {
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("educationSystemId").is(systemId));
			}
		return mongoTemplate.find(query,GradeDetails.class,"grade");
	}

	@Override
	public void saveAll(List<GradeDetails> grades) {
		gradeDetailRepository.saveAll(grades);
	}

	@Override
	public GradeDetails findByCountryNameAndStateNameAndGradeAndEducationSystem(String countryName,
			String stateName, String grade, EducationSystem educationSystem) {
		return gradeDetailRepository.findByCountryNameAndStateNameAndGradeAndEducationSystem(countryName,
				stateName, grade, educationSystem);
	}
}
