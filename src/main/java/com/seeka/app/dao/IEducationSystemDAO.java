package com.seeka.app.dao;import java.math.BigInteger;


import java.util.List;


import com.seeka.app.bean.EducationSystem;

public interface IEducationSystemDAO {
	public void save(EducationSystem hobbiesObj);
	public void update(EducationSystem hobbiesObj);
	public EducationSystem get(BigInteger id);
	public List<EducationSystem> getAll();
	public List<EducationSystem> getAllGlobeEducationSystems();
	
}
