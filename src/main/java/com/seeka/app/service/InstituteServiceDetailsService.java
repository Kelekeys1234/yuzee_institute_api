package com.seeka.app.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteService;
import com.seeka.app.dao.InstituteServiceDetailsDAO;

@Service
@Transactional
public class InstituteServiceDetailsService implements IInstituteServiceDetailsService {

    @Autowired
    private InstituteServiceDetailsDAO iInstituteServiceDetailsDAO;

    @Override
    public void save(InstituteService instituteService) {
        iInstituteServiceDetailsDAO.save(instituteService);
    }

    @Override
    public void update(InstituteService instituteService) {
        iInstituteServiceDetailsDAO.update(instituteService);
    }

    @Override
    public InstituteService get(String id) {
        return iInstituteServiceDetailsDAO.get(id);
    }

    @Override
    public List<InstituteService> getAll() {
        return iInstituteServiceDetailsDAO.getAll();
    }

    @Override
    public List<String> getAllServices(String instituteId) {
    	List<String> instituteServiceNames = new ArrayList<>();
    	List<InstituteService> instituteServices = iInstituteServiceDetailsDAO.getAllServices(instituteId);
    	if(!CollectionUtils.isEmpty(instituteServices)) {
			instituteServices.stream().forEach(instituteService -> {
				instituteServiceNames.add(instituteService.getServiceName());
			});
		}
        return instituteServiceNames;
    }

}
