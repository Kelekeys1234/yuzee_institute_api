package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.InstituteKeywords;

public interface InstituteKeywordDao {
	
	public void save(InstituteKeywords obj);
	
	public void update(InstituteKeywords obj);

	public List<InstituteKeywords> getAll();
	
}
