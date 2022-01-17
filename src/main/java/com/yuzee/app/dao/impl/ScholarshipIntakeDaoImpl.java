package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.ScholarshipIntake;
import com.yuzee.app.dao.ScholarshipIntakeDao;
import com.yuzee.app.repository.ScholarshipIntakeRepository;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScholarshipIntakeDaoImpl implements ScholarshipIntakeDao {
	
	@Autowired
	private MessageTranslator messageTranslator;
	
	@Autowired
	private ScholarshipIntakeRepository scholarshipIntakeRepository;

	@Override
	public List<ScholarshipIntake> saveAll(List<ScholarshipIntake> courseSubjects) throws ValidationException {
		try {
			return scholarshipIntakeRepository.saveAll(courseSubjects);
		} catch (DataIntegrityViolationException e) {
			log.error(messageTranslator.toLocale("scolarship-intake.already.exist.type",Locale.US));
			throw new ValidationException(
					messageTranslator.toLocale("scolarship-intake.already.exist.type"));
		}
	}

	

	@Override
	public List<ScholarshipIntake> findByScholarshipIdAndIdIn(String courseId, List<String> ids) {
		return scholarshipIntakeRepository.findByScholarshipIdAndIdIn(courseId, ids);
	}

	@Transactional
	@Override
	public void deleteByScholarshipIdAndIdIn(String courseId, List<String> ids) {
		scholarshipIntakeRepository.deleteByScholarshipIdAndIdIn(courseId, ids);
	}
}
