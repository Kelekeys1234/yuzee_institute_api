package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.Institute;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;

public interface IInstituteDAO {
	
	public void save(Institute obj);
	public void update(Institute obj);
	public Institute get(UUID id);
	public List<Institute> getAllInstituteByCountry(UUID countryId);	
	public List<Institute> getAll();
	public List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey);
	public List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj);
	public InstituteResponseDto getInstituteByID(UUID instituteId);
}
