package com.seeka.app.service;

import java.util.Map;

public interface ISkillService {

	Integer getAllSkillNamesCount(String searchString);
	
	public Map<String, Object> autoSearch(Integer pageNumber, Integer pageSize, String searchKey);
}
