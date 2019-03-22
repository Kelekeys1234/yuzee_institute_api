package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Institute;
import com.seeka.app.dto.InstituteSearchResultDto;

public interface IInstituteDAO {
	
	public void save(Institute obj);
	public void update(Institute obj);
	public Institute get(Integer id);
	public List<Institute> getAllInstituteByCountry(Integer countryId);	
	public List<Institute> getAll();
	public List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey);
	
}
