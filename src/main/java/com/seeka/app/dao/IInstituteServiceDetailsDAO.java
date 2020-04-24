package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.InstituteService;

public interface IInstituteServiceDetailsDAO {

    void save(InstituteService obj);

    void update(InstituteService obj);

    InstituteService get(String id);

    List<InstituteService> getAll();

    List<String> getAllServices(String instituteId);
    
    public List<InstituteService> getAllInstituteService(String instituteId);
    
    public void saveInstituteServices(List<InstituteService> listOfInstituteService);

    public void deleteServiceByIdAndInstituteId (String serviceId, String instituteId);
}
