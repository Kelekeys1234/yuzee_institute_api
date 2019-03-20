package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.InstituteImages;

public interface IInstituteImagesService {
	
	public void save(InstituteImages obj);
	public void update(InstituteImages obj);
	public InstituteImages get(Integer id);
	public List<InstituteImages> getAll(); 
}
