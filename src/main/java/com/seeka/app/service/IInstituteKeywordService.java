package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.InstituteKeywords;

public interface IInstituteKeywordService {
	
	public void save(InstituteKeywords obj);
	public void update(InstituteKeywords obj);
	public List<InstituteKeywords> getAll();
	//public List<InstituteKeywords> searchCourseKeyword(String keyword);
}
