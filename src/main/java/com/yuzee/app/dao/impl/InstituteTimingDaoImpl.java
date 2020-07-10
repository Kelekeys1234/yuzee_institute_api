package com.yuzee.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.InstituteTiming;
import com.yuzee.app.dao.InstituteTimingDao;
import com.yuzee.app.repository.InstituteTimingRepository;

@Component
public class InstituteTimingDaoImpl implements InstituteTimingDao {

	@Autowired
	private InstituteTimingRepository instituteTimingRepository;
	
	@Override
	public void saveInstituteTiming(InstituteTiming instituteTiming) {
		instituteTimingRepository.save(instituteTiming);
	}

	@Override
	public InstituteTiming getInstituteTimeByInstituteId(String instituteId) {
		return instituteTimingRepository.findByInstituteId(instituteId);
	}

}
