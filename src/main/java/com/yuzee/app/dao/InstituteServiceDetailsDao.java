package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.InstituteService;

public interface InstituteServiceDetailsDao {

    public void save(InstituteService obj);

    public void update(InstituteService obj);

    public InstituteService get(String id);

    public List<InstituteService> getAll();

    public List<InstituteService> getAllServices(String instituteId);
    
    public List<InstituteService> getAllInstituteService(String instituteId);
    
    public void saveInstituteServices(List<InstituteService> listOfInstituteService);

    public void deleteServiceByIdAndInstituteId (String serviceId, String instituteId);
}
