package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.InstituteLevel;

public interface IInstituteLevelService {

	public void save(InstituteLevel obj);
	public void update(InstituteLevel obj);
	public InstituteLevel get(BigInteger id);
	public List<InstituteLevel> getAllLevelByInstituteId(BigInteger instituteId);
}
