package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.InstituteLevel;

public interface IInstituteLevelDAO {
	
	public void save(InstituteLevel obj);
	public void update(InstituteLevel obj);
	public InstituteLevel get(UUID id);
	public List<InstituteLevel> getAllLevelByInstituteId(UUID instituteId);
}
