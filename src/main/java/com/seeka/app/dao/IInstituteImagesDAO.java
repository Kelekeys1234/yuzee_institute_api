package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteImages;

public interface IInstituteImagesDAO {
	void save(InstituteImages obj);

	void update(InstituteImages obj);

	InstituteImages get(BigInteger id);

	List<InstituteImages> getAll();

	List<InstituteImages> getInstituteImageListBasedOnId(BigInteger instituteId);
}
