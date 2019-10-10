package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Scholarship;
import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.dto.ScholarshipFilterDto;

public interface IScholarshipDAO {

	public void save(Scholarship obj);
	
	public List<Scholarship> getAll(Integer pageNumber, Integer pageSize);

	public Scholarship get(BigInteger id);
	
	public void deleteScholarship(Scholarship scholarship);
	
	public void updateScholarship(Scholarship scholarship, ScholarshipDto scholarshipdto);
	
	public Scholarship findById(BigInteger id);
	
	public int findTotalCount();
	
	List<ScholarshipDto> getScholarshipBySearchKey(String searchKey);

    public List<Scholarship> scholarshipFilter(int startIndex, Integer maxSizePerPage, ScholarshipFilterDto scholarshipFilterDto);

    public int findTotalCountOfSchoolarship(ScholarshipFilterDto scholarshipFilterDto);

    public List<Scholarship> autoSearch(int startIndex, Integer pageSize, String searchKey);

    public int findTotalCountOfScholarshipAutoSearch(String searchKey);

	List<BigInteger> getRandomScholarShipsForCountry(List<BigInteger> countryId, Integer limit);

	List<Scholarship> getAllScholarshipDetailsFromId(List<BigInteger> recommendedScholarships);

	List<BigInteger> getRandomScholarships(int i);
}
