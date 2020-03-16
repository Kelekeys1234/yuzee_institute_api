package com.seeka.app.service;

import java.util.List;

import com.seeka.app.dto.SkillDto;

public interface ISkillService {

	int getDistinctSkillsCount(String skillName);
	
	List<SkillDto> getDistinctSkillsList(Integer startIndex, Integer pageSize, String skillName) ;
}
