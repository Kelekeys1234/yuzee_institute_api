package com.yuzee.app.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.bean.Subject;
import com.yuzee.app.dao.SubjectDao;
import com.yuzee.app.repository.SubjectRepository;

@Component
public class SubjectDaoImpl implements SubjectDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private SubjectRepository subjectRepository;

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

	@Override
	public Page<Subject> findByNameContainingIgnoreCaseAndEducationSystemId(String name, String educationSystemId,
			Pageable pageable) {
		return subjectRepository.findBySubjectNameContainingIgnoreCaseAndEducationSystemId(name, educationSystemId, pageable);
	}
}
