package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.dto.SkillDto;

public interface ISkillDao {

	int getDistinctSkillsCountByName(String skillName);

	List<SkillDto> getDistinctSkillsListByName(Integer startIndex, Integer pageSize, String skillName);
}
