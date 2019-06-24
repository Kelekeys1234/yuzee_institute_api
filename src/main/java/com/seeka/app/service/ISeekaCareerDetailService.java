package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.SeekaCareerDetail;

public interface ISeekaCareerDetailService {
	public List<SeekaCareerDetail> getAll();
	public SeekaCareerDetail get(BigInteger id);
	public void save(SeekaCareerDetail obj);
}
