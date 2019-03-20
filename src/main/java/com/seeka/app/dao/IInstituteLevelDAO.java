package com.seeka.app.dao;

import com.seeka.app.bean.InstituteLevel;

public interface IInstituteLevelDAO {
	
	public void save(InstituteLevel obj);
	public void update(InstituteLevel obj);
	public InstituteLevel get(Integer id);
}
