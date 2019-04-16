package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.InstituteKeyword;

public interface IInstituteKeywordService {
	
	public void save(InstituteKeyword obj);
	public void update(InstituteKeyword obj);
	public List<InstituteKeyword> getAll();
	//public List<InstituteKeyword> searchCourseKeyword(String keyword);
}
