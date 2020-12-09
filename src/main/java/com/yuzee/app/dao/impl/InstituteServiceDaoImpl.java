package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.InstituteService;
import com.yuzee.app.dao.InstituteServiceDao;
import com.yuzee.app.repository.InstituteServiceRepository;

@Service
public class InstituteServiceDaoImpl implements InstituteServiceDao {

	@Autowired
	private InstituteServiceRepository instituteServiceRepository;

	@Override
	public InstituteService get(String id) {
		return instituteServiceRepository.getOne(id);
	}

	@Override
	public List<InstituteService> getAllInstituteService(String instituteId) {
		return instituteServiceRepository.findByInstituteId(instituteId);
	}

	@Override
	public InstituteService findByInstituteIdAndServiceId(String instituteId, String serviceId) {
		return instituteServiceRepository.findByInstituteIdAndServiceId(instituteId, serviceId);
	}

	@Override
	public InstituteService save(InstituteService instituteService) {
		return instituteServiceRepository.save(instituteService);
	}

	@Transactional
	@Override
	public void delete(String instituteServiceId) {
		instituteServiceRepository.deleteById(instituteServiceId);
	}

}
