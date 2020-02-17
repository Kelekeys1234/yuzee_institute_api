package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.SeekaCareerDetail;

public interface ISeekaCareerDetailService {
	public List<SeekaCareerDetail> getAll();
	public SeekaCareerDetail get(String id);
	public void save(SeekaCareerDetail obj);
}
