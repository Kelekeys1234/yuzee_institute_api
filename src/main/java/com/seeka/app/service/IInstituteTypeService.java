package com.seeka.app.service;import java.math.BigInteger;

import com.seeka.app.bean.InstituteType;

public interface IInstituteTypeService {

	void save(InstituteType obj);
	void update(InstituteType obj);
	InstituteType get(BigInteger id);
}
