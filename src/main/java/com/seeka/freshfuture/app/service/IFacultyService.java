package com.seeka.freshfuture.app.service;

import java.util.List;

import com.seeka.freshfuture.app.bean.Faculty;

public interface IFacultyService {
	
	public void save(Faculty obj);
	public void update(Faculty obj);
	public Faculty get(Integer id);
	public List<Faculty> getAll();
}
