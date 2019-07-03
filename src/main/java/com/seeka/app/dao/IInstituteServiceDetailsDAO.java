package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteService;

public interface IInstituteServiceDetailsDAO {

    void save(InstituteService obj);

    void update(InstituteService obj);

    InstituteService get(BigInteger id);

    List<InstituteService> getAll();

    List<String> getAllServices(BigInteger instituteId);
}
