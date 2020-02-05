package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteLevel;

public interface IInstituteLevelDAO {

	void save(InstituteLevel obj);

	void update(InstituteLevel obj);

	InstituteLevel get(BigInteger id);

	List<InstituteLevel> getAllLevelByInstituteId(String instituteId);

	void deleteInstituteLevel(String instituteId);
}
