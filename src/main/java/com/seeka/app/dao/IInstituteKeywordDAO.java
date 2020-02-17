package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.InstituteKeywords;

public interface IInstituteKeywordDAO {
	
	public void save(InstituteKeywords obj);
	public void update(InstituteKeywords obj);
	public List<InstituteKeywords> getAll();
	//public List<InstituteKeywords> searchCourseKeyword(String keyword);
	
}
