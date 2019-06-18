package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteService;

public interface IInstituteServiceDetailsService {

    public void save(com.seeka.app.bean.InstituteService obj);

    public void update(InstituteService obj);

    public InstituteService get(BigInteger id);

    public List<InstituteService> getAll();

    public List<String> getAllServices(BigInteger instituteId);
}
