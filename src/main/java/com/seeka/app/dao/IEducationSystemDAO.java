package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.bean.Subject;

public interface IEducationSystemDAO {
	void save(EducationSystem hobbiesObj);

	void update(EducationSystem hobbiesObj);

	EducationSystem get(BigInteger id);

	List<EducationSystem> getAll();

	List<EducationSystem> getAllGlobeEducationSystems();

	List<EducationSystem> getEducationSystemsByCountryId(BigInteger countryId);

	List<Subject> getSubjectByEducationSystem(BigInteger educationSystemId);

	List<Subject> getSubject();

}
