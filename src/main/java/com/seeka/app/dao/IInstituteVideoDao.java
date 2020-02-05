package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteVideos;

public interface IInstituteVideoDao {

	void save(InstituteVideos instituteVideo);

	List<InstituteVideos> findByInstituteId(String id);

}
