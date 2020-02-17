package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.InstituteLevel;

public interface IInstituteLevelService {

	void save(InstituteLevel obj);

	void update(InstituteLevel obj);

	InstituteLevel get(String id);

	List<InstituteLevel> getAllLevelByInstituteId(String instituteId);

	void deleteInstituteLevel(String instituteId);
}
