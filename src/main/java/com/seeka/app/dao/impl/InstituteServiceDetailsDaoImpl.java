package com.seeka.app.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.InstituteService;
import com.seeka.app.dao.InstituteServiceDetailsDao;
import com.seeka.app.repository.InstituteServiceRepository;

@Component
public class InstituteServiceDetailsDaoImpl implements InstituteServiceDetailsDao {
	
	@Autowired
	private InstituteServiceRepository instituteServiceRepository;
	
	@Override
	public void save(InstituteService obj) {	
		instituteServiceRepository.save(obj);	   					
	}
	
	@Override
	public void update(InstituteService obj) {	
		instituteServiceRepository.save(obj);	   					
	}
	
	@Override
	public InstituteService get(String id) {	
		Optional<InstituteService> optionalInstituteService = instituteServiceRepository.findById(id);
		return optionalInstituteService.get();
	}
	
	@Override
	public List<InstituteService> getAll() {
		return instituteServiceRepository.findAll();
	}
	
	@Override
	public List<InstituteService> getAllServices(String instituteId) {
		List<InstituteService> instituteServices = instituteServiceRepository.findByInstituteId(instituteId);
		return instituteServices;
	}

	@Override
	public List<InstituteService> getAllInstituteService(String instituteId) {
		return instituteServiceRepository.findByInstituteId(instituteId);
	}

	@Override
	public void saveInstituteServices(List<InstituteService> listOfInstituteService) {
		instituteServiceRepository.saveAll(listOfInstituteService);
	}

	@Override
	public void deleteServiceByIdAndInstituteId(String id, String instituteId) {
		instituteServiceRepository.deleteByIdAndInstituteId(id, instituteId);
	}
	
}
