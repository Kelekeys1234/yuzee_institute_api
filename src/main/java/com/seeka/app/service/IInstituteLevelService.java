package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteLevel;

public interface IInstituteLevelService {

	void save(InstituteLevel obj);

	void update(InstituteLevel obj);

	InstituteLevel get(BigInteger id);

	List<InstituteLevel> getAllLevelByInstituteId(BigInteger instituteId);

	void deleteInstituteLevel(BigInteger instituteId);
}
