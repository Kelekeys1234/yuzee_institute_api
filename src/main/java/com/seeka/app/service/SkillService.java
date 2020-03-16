package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seeka.app.dao.ISkillDao;
import com.seeka.app.dto.SkillDto;

@Service
public class SkillService implements ISkillService {

	@Autowired
	private ISkillDao iSkillDao;

	@Override
	public List<SkillDto> getDistinctSkillsList(Integer startIndex, Integer pageSize, String skillName) {
		return iSkillDao.getDistinctSkillsListByName(startIndex, pageSize, skillName);
	}

	@Override
	public int getDistinctSkillsCount(String skillName) {
		return iSkillDao.getDistinctSkillsCountByName(skillName);
	}
}
