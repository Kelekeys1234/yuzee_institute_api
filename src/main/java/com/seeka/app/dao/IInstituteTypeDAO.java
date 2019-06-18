package com.seeka.app.dao;import java.math.BigInteger;



import com.seeka.app.bean.InstituteType;

public interface IInstituteTypeDAO {
	
	public void save(InstituteType obj);
	public void update(InstituteType obj);
	public InstituteType get(BigInteger id);
}
