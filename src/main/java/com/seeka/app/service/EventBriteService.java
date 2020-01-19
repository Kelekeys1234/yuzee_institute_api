package com.seeka.app.service;

import java.util.List;

import com.seeka.app.dto.EventBriteDto;

public interface EventBriteService {

	public List<EventBriteDto> getAllCategories();
	
	public Integer getTotalCount();
}
