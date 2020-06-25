package com.seeka.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.InstituteTiming;
import com.seeka.app.dao.InstituteTimingDao;
import com.seeka.app.repository.InstituteTimingRepository;

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
