package com.seeka.app.dao;

import com.seeka.app.bean.InstituteType;

public interface IInstituteTypeDAO {
	
	public void save(InstituteType obj);
	public void update(InstituteType obj);
	public InstituteType get(Integer id);
}
