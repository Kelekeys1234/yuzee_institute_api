package com.seeka.app.dao;import java.math.BigInteger;



import com.seeka.app.bean.InstituteType;

public interface IInstituteTypeDAO {
	
	void save(InstituteType obj);
	void update(InstituteType obj);
	InstituteType get(BigInteger id);
}
