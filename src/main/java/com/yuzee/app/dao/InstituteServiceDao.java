package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.InstituteService;

public interface InstituteServiceDao {

	public InstituteService get(String id);

	public List<InstituteService> getAllInstituteService(String instituteId);

	public List<InstituteService> saveAll(List<InstituteService> listOfInstituteService);


	public void delete(String instituteServiceId);
}
