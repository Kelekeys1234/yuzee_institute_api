package com.seeka.freshfuture.app.service;

import com.seeka.freshfuture.app.bean.InstituteType;

public interface IInstituteTypeService {

	public void save(InstituteType obj);
	public void update(InstituteType obj);
	public InstituteType get(Integer id);
}
