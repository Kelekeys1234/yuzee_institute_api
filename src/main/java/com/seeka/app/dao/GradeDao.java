package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.GradeDetails;

public interface GradeDao {

	public String getGradeDetails(final String countryId, final String educationSystemId, final String grade);
	
	public List<GradeDetails> getGrades(final String countryId, final String systemId);
}
