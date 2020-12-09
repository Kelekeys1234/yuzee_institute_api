package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.InstituteService;

public interface InstituteServiceDao {

	public InstituteService get(String id);

	public List<InstituteService> getAllInstituteService(String instituteId);

	public InstituteService findByInstituteIdAndServiceId(String instituteId, String serviceId);

	public InstituteService save(InstituteService instituteService);

	public void delete(String instituteServiceId);
}
