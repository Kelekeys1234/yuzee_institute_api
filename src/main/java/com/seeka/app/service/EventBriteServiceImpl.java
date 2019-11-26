/**
 * 
 */
package com.seeka.app.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author het.shah
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class EventBriteServiceImpl implements EventBriteService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.seeka.app.service.EventBriteService#getAllCategories()
	 */
	/**
	 * As per the requirement and JIRA ticket created SBA - 143 , Hardcoding all the
	 * categories here for eventbrite
	 */
	String[] categories = new String[] { "Family and Education", "School Activities", "Health", "Charity & Causes",
			"Film and Media", "Food & Drinks", "Hobbies", "Performance & Visual Arts", "Science and Tech",
			"Sports and Fitness" };
	
	@Override
	public List<String> getAllCategories() {
		List<String> categoriesList = Arrays.asList(categories);
		return categoriesList;
	}
	
	@Override
	public Integer getTotalCount() {
		Integer categoryCount = categories.length;
		return categoryCount;
	}

}
