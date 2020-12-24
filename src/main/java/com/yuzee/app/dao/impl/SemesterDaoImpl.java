package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.Semester;
import com.yuzee.app.dao.SemesterDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.SemesterRepository;

import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@Slf4j
public class SemesterDaoImpl implements SemesterDao {

	@Autowired
	private SemesterRepository semesterRepository;

	@Override
	public List<Semester> saveAll(List<Semester> semesters) throws ValidationException {
		try {
			return semesterRepository.saveAll(semesters);
		} catch (DataIntegrityViolationException ex) {
			log.error("one or more semesters contains already existing name");
			throw new ValidationException("one or more semesters contains already existing name");
		}
	}

	@Override
	public Semester save(Semester semester) throws ValidationException {
		try {
			return semesterRepository.save(semester);
		} catch (DataIntegrityViolationException ex) {
			log.error("semester with same name already exists.");
			throw new ValidationException("semester with same name already exists.");
		}
	}

	@Override
	public Optional<Semester> getById(String id) {
		return semesterRepository.findById(id);
	}

	@Override
	public Page<Semester> getAll(Pageable pageable) {
		return semesterRepository.findAll(pageable);
	}

	@Override
	public void delete(String id) {
		semesterRepository.deleteById(id);
		
	}
}
