package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.InstituteService;

public interface InstituteServiceDao {

	public InstituteService get(String id);

	public List<InstituteService> getAll();

	public List<InstituteService> getAllServices(String instituteId);

	public List<InstituteService> getAllInstituteService(String instituteId);

	public void saveAll(List<InstituteService> listOfInstituteService);

	public void deleteByInstituteIdAndServiceByIds(String instituteId, List<String> serviceIds);

}
