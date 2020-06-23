package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.InstituteService;

public interface InstituteServiceDetailsDAO {

    public void save(InstituteService obj);

    public void update(InstituteService obj);

    public InstituteService get(String id);

    public List<InstituteService> getAll();

    public List<InstituteService> getAllServices(String instituteId);
    
    public List<InstituteService> getAllInstituteService(String instituteId);
    
    public void saveInstituteServices(List<InstituteService> listOfInstituteService);

    public void deleteServiceByIdAndInstituteId (String serviceId, String instituteId);
}
