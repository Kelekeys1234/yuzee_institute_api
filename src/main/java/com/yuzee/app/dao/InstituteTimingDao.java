package com.yuzee.app.dao;

import com.yuzee.app.bean.InstituteTiming;

public interface InstituteTimingDao {

	public void saveInstituteTiming(InstituteTiming instituteTiming);
	
	public InstituteTiming getInstituteTimeByInstituteId(String instituteId);
}
