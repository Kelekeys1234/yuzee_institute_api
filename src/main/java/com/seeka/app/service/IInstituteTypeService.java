package com.seeka.app.service;

import com.seeka.app.bean.InstituteType;

public interface IInstituteTypeService {

	public void save(InstituteType obj);
	public void update(InstituteType obj);
	public InstituteType get(Integer id);
}
