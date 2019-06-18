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
    IInstituteServiceDetailsDAO dao;

    @Override
    public void save(InstituteService obj) {
        dao.save(obj);
    }

    @Override
    public void update(InstituteService obj) {
        dao.update(obj);
    }

    @Override
    public InstituteService get(BigInteger id) {
        return dao.get(id);
    }

    @Override
    public List<InstituteService> getAll() {
        return dao.getAll();
    }

    @Override
    public List<String> getAllServices(BigInteger instituteId) {
        return dao.getAllServices(instituteId);
    }

}
