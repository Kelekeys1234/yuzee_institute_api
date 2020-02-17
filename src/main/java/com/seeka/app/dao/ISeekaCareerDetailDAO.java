package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.SeekaCareerDetail;

public interface ISeekaCareerDetailDAO {
	public List<SeekaCareerDetail> getAll();
	public SeekaCareerDetail get(String id);
	public void save(SeekaCareerDetail obj); 
}
