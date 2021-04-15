package com.yuzee.app.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.bean.Subject;
import com.yuzee.app.dao.SubjectDao;

@Component
public class SubjectDaoImpl implements SubjectDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveSubjects(List<Subject> subjectList, List<EducationSystem> educationSystems) {
		Session session = sessionFactory.getCurrentSession();

		for (EducationSystem educationSystem : educationSystems) {

			for (Subject subject : subjectList) {

				Subject subjectObj = new Subject();
				subjectObj.setSubjectName(subject.getSubjectName());
				subjectObj.setEducationSystemId(educationSystem.getId());
				session.save(subjectObj);
			}
		}
	}
}
