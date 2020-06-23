package com.seeka.app.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.InstituteEnglishRequirements;
import com.seeka.app.dao.InstituteEnglishRequirementsDao;
import com.seeka.app.repository.InstituteEnglishRequirementsRepository;

@Component
public class InstituteEnglishRequirementsDaoImpl implements InstituteEnglishRequirementsDao {
	
	@Autowired
	private InstituteEnglishRequirementsRepository instituteEnglishRequirementsRepository;

	@Override
	public InstituteEnglishRequirements addUpdateInsituteEnglishRequirements(
			InstituteEnglishRequirements instituteEnglishRequirements) {
		return instituteEnglishRequirementsRepository.save(instituteEnglishRequirements);
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
