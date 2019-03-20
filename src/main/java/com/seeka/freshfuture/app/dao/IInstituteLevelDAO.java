package com.seeka.freshfuture.app.dao;

import com.seeka.freshfuture.app.bean.InstituteLevel;

public interface IInstituteLevelDAO {
	
	public void save(InstituteLevel obj);
	public void update(InstituteLevel obj);
	public InstituteLevel get(Integer id);
}
