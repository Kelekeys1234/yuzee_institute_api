package com.yuzee.app.dao;import java.util.List;

import com.yuzee.app.bean.InstituteKeywords;

public interface InstituteKeywordDao {
	
	public void save(InstituteKeywords obj);
	
	public void update(InstituteKeywords obj);

	public List<InstituteKeywords> getAll();
	
}
