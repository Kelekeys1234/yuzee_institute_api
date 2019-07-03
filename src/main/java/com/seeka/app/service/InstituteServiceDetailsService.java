package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteService;
import com.seeka.app.dao.IInstituteServiceDetailsDAO;

@Service
@Transactional
public class InstituteServiceDetailsService implements IInstituteServiceDetailsService {

    @Autowired
    private IInstituteServiceDetailsDAO iInstituteServiceDetailsDAO;

    @Override
    public void save(InstituteService instituteService) {
        iInstituteServiceDetailsDAO.save(instituteService);
    }

    @Override
    public void update(InstituteService instituteService) {
        iInstituteServiceDetailsDAO.update(instituteService);
    }

    @Override
    public InstituteService get(BigInteger id) {
        return iInstituteServiceDetailsDAO.get(id);
    }

    @Override
    public List<InstituteService> getAll() {
        return iInstituteServiceDetailsDAO.getAll();
    }

    @Override
    public List<String> getAllServices(BigInteger instituteId) {
        return iInstituteServiceDetailsDAO.getAllServices(instituteId);
    }

}
