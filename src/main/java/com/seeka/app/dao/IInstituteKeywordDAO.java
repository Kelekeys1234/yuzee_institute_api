package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.InstituteKeyword;

public interface IInstituteKeywordDAO {
	
	public void save(InstituteKeyword obj);
	public void update(InstituteKeyword obj);
	public List<InstituteKeyword> getAll();
	//public List<InstituteKeyword> searchCourseKeyword(String keyword);
	
}
