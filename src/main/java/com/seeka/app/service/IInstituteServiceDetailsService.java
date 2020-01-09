package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteService;

public interface IInstituteServiceDetailsService {

    void save(com.seeka.app.bean.InstituteService obj);

    void update(InstituteService obj);

    InstituteService get(BigInteger id);

    List<InstituteService> getAll();

    List<String> getAllServices(BigInteger instituteId);
}
