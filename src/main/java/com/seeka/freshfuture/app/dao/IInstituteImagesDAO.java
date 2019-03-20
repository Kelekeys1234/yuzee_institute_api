package com.seeka.freshfuture.app.dao;

import java.util.List;

import com.seeka.freshfuture.app.bean.InstituteImages;

public interface IInstituteImagesDAO {
	public void save(InstituteImages obj);
	public void update(InstituteImages obj);
	public InstituteImages get(Integer id);
	public List<InstituteImages> getAll();	
}
