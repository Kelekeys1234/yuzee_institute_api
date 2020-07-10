package com.yuzee.app.dao;

import java.util.List;
import java.util.Optional;

import com.yuzee.app.bean.InstituteEnglishRequirements;

public interface InstituteEnglishRequirementsDao {

	public InstituteEnglishRequirements addUpdateInsituteEnglishRequirements(InstituteEnglishRequirements instituteEnglishRequirements);
	
	public Optional<InstituteEnglishRequirements> getInsituteEnglishRequirementsById (String englishRequirementsId);
	
	public List<InstituteEnglishRequirements> getInsituteEnglishRequirementsByInstituteId (String instituteId) ;
	
	public void deleteInstituteEnglishRequirementsById (String englishRequirementsId);
}
