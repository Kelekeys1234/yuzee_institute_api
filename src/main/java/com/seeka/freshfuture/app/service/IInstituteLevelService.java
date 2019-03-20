package com.seeka.freshfuture.app.service;

import com.seeka.freshfuture.app.bean.InstituteLevel;

public interface IInstituteLevelService {

	public void save(InstituteLevel obj);
	public void update(InstituteLevel obj);
	public InstituteLevel get(Integer id);
}
