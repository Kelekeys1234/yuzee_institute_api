package com.seeka.app.dao;

import java.util.List;
import java.util.Optional;

import com.seeka.app.bean.InstituteEnglishRequirements;

public interface InstituteEnglishRequirementsDao {

	public InstituteEnglishRequirements addUpdateInsituteEnglishRequirements(InstituteEnglishRequirements instituteEnglishRequirements);
	
	public Optional<InstituteEnglishRequirements> getInsituteEnglishRequirementsById (String englishRequirementsId);
	
	public List<InstituteEnglishRequirements> getInsituteEnglishRequirementsByInstituteId (String instituteId) ;
	
	public void deleteInstituteEnglishRequirementsById (String englishRequirementsId);
}
