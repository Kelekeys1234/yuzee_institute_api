package com.seeka.freshfuture.app.dao;

import com.seeka.freshfuture.app.bean.InstituteType;

public interface IInstituteTypeDAO {
	
	public void save(InstituteType obj);
	public void update(InstituteType obj);
	public InstituteType get(Integer id);
}
