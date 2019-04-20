package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.InstituteImages;

public interface IInstituteImagesDAO {
	public void save(InstituteImages obj);
	public void update(InstituteImages obj);
	public InstituteImages get(UUID id);
	public List<InstituteImages> getAll();	
}
