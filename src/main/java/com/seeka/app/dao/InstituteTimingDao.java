package com.seeka.app.dao;

import com.seeka.app.bean.InstituteTiming;

public interface InstituteTimingDao {

	public void saveInstituteTiming(InstituteTiming instituteTiming);
	
	public InstituteTiming getInstituteTimeByInstituteId(String instituteId);
}
