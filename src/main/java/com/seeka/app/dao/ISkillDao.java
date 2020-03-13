package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.dto.SkillDto;

public interface ISkillDao {

	Integer getAllSkillNamesCount(String searchString);
	
	List<SkillDto> autoSearch(Integer pageNumber, Integer pageSize, String searchKey);
}
