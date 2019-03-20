package com.seeka.app.service;

import com.seeka.app.bean.InstituteLevel;

public interface IInstituteLevelService {

	public void save(InstituteLevel obj);
	public void update(InstituteLevel obj);
	public InstituteLevel get(Integer id);
}
