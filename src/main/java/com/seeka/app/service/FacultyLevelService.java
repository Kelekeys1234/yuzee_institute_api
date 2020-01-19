package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.FacultyLevel;
import com.seeka.app.dao.IFacultyLevelDAO;

@Service
@Transactional
public class FacultyLevelService implements IFacultyLevelService {

	@Autowired
	private IFacultyLevelDAO dao;

	@Override
	public void save(final FacultyLevel obj) {
		dao.save(obj);
	}

	@Override
	public void update(final FacultyLevel obj) {
		dao.update(obj);
	}

	@Override
	public FacultyLevel get(final BigInteger id) {
		return dao.get(id);
	}

	@Override
	public List<FacultyLevel> getAll() {
		return dao.getAll();
	}

	@Override
	public List<FacultyLevel> getFacultyByCountryIdAndCourseTypeId(final BigInteger countryID, final BigInteger courseTypeId) {
		return dao.getFacultyByCountryIdAndCourseTypeId(countryID, courseTypeId);
	}

	@Override
	public List<FacultyLevel> getAllFacultyLevelByInstituteId(final BigInteger instituteId) {
		return dao.getAllFacultyLevelByInstituteId(instituteId);
	}

	public void deleteFacultyLevel(final BigInteger instituteId) {
		dao.deleteFacultyLevel(instituteId);
	}

}
