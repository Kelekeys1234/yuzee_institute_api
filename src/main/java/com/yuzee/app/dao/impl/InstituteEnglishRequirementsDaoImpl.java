package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.InstituteEnglishRequirements;
import com.yuzee.app.dao.InstituteEnglishRequirementsDao;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.app.repository.InstituteEnglishRequirementsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstituteEnglishRequirementsDaoImpl implements InstituteEnglishRequirementsDao {

	@Autowired
	private InstituteEnglishRequirementsRepository instituteEnglishRequirementsRepository;

	@Override
	public InstituteEnglishRequirements addUpdateInsituteEnglishRequirements(
			InstituteEnglishRequirements instituteEnglishRequirements) throws ValidationException {
		try {
			return instituteEnglishRequirementsRepository.save(instituteEnglishRequirements);
		} catch (DataIntegrityViolationException ex) {
			log.error("Exam with the same name already present");
			throw new ValidationException("Exam with the same name already present");
		}
	}

	@Override
	public Optional<InstituteEnglishRequirements> getInsituteEnglishRequirementsById(String englishRequirementsId) {
		return instituteEnglishRequirementsRepository.findById(englishRequirementsId);
	}

	@Override
	public List<InstituteEnglishRequirements> getInsituteEnglishRequirementsByInstituteId(String instituteId) {
		return instituteEnglishRequirementsRepository.findByInstituteId(instituteId);
	}

	@Override
	public void deleteInstituteEnglishRequirementsById(String englishRequirementsId) {
		instituteEnglishRequirementsRepository.deleteById(englishRequirementsId);
	}

}
