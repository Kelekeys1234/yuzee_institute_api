package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.GradeDetails;
import com.yuzee.app.dto.GradeDto;

public interface GradeDao {

	public String getGradeDetails(final String countryId, final String educationSystemId, final String grade);
	
	public List<GradeDetails> getGrades(final String countryId, final String systemId);

	public void saveGrade(List<GradeDetails> grades);
}
