package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.bean.Scholarship;
import com.seeka.app.dto.ScholarshipDto;

public interface IScholarshipDAO {

	public void save(Scholarship obj);
	
	public List<Scholarship> getAll(Integer pageNumber, Integer pageSize);

	public Scholarship get(BigInteger id);
	
	public void deleteScholarship(Scholarship scholarship);
	
	public void updateScholarship(Scholarship scholarship, ScholarshipDto scholarshipdto);
	
	public Scholarship findById(BigInteger id);
	
	public int findTotalCount();
	
	List<ScholarshipDto> getScholarshipBySearchKey(String searchKey);
}
