package com.seeka.app.dao;

import java.util.UUID;

import com.seeka.app.bean.InstituteType;

public interface IInstituteTypeDAO {
	
	public void save(InstituteType obj);
	public void update(InstituteType obj);
	public InstituteType get(UUID id);
}
