package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.Subject;
import com.yuzee.app.dao.SubjectDao;
import com.yuzee.app.repository.SubjectRepository;

@Component
public class SubjectDaoImpl implements SubjectDao {

	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	public void saveAll(List<Subject> subjects) {
		subjectRepository.saveAll(subjects);
	}

	@Override
	public Page<Subject> findByNameContainingIgnoreCaseAndEducationSystemId(String name, String educationSystemId,
			Pageable pageable) {
		return subjectRepository.findByNameContainingIgnoreCaseAndEducationSystemId(name, educationSystemId, pageable);
	}

	@Override
	public Subject findByNameAndEducationSystemId(String name, String educationSystemId) {
		return subjectRepository.findByNameAndEducationSystemId(name, educationSystemId);
	}
}
