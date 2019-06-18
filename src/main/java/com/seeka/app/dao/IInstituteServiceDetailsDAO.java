package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteService;

public interface IInstituteServiceDetailsDAO {

    public void save(InstituteService obj);

    public void update(InstituteService obj);

    public InstituteService get(BigInteger id);

    public List<InstituteService> getAll();

    public List<String> getAllServices(BigInteger instituteId);
}
