/**
 * 
 */
package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.dto.EventBriteDto;

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
//	String[] categories = new String[] { "Family and Education", "School Activities", "Health", "Charity & Causes",
//			"Film and Media", "Food & Drinks", "Hobbies", "Performance & Visual Arts", "Science and Tech",
//			"Sports and Fitness" };
	
	
	@Override
	public List<EventBriteDto> getAllCategories() {
		
		EventBriteDto eventBiteDto1 = new EventBriteDto();
		eventBiteDto1.setId(115);
		eventBiteDto1.setName("Family and Education");
		eventBiteDto1.setName_localized("Family & Education");
		eventBiteDto1.setResourceUri( "https://www.eventbriteapi.com/v3/categories/115/");
		eventBiteDto1.setShort_name("Family & Education");
		eventBiteDto1.setShort_name_localized("Family & Education");

		EventBriteDto eventBiteDto2 = new EventBriteDto();
		eventBiteDto2.setId(120);
		eventBiteDto2.setName("School Activities");
		eventBiteDto2.setName_localized("School Activities");
		eventBiteDto2.setResourceUri( "https://www.eventbriteapi.com/v3/categories/120/");
		eventBiteDto2.setShort_name("School Activities");
		eventBiteDto2.setShort_name_localized("School Activities");
		
		EventBriteDto eventBiteDto3 = new EventBriteDto();
		eventBiteDto3.setId(107);
		eventBiteDto3.setName("Health & Wellness");
		eventBiteDto3.setName_localized("Health & Wellness");
		eventBiteDto3.setResourceUri( "https://www.eventbriteapi.com/v3/categories/107/");
		eventBiteDto3.setShort_name("Health");
		eventBiteDto3.setShort_name_localized("Health");
		
		EventBriteDto eventBiteDto4 = new EventBriteDto();
		eventBiteDto4.setId(111);
		eventBiteDto4.setName("Charity & Causes");
		eventBiteDto4.setName_localized("Charity & Causes");
		eventBiteDto4.setResourceUri( "https://www.eventbriteapi.com/v3/categories/111/");
		eventBiteDto4.setShort_name("Charity & Causes");
		eventBiteDto4.setShort_name_localized("Charity & Causes");
		
		EventBriteDto eventBiteDto5 = new EventBriteDto();
		eventBiteDto5.setId(104);
		eventBiteDto5.setName("Film, Media & Entertainment");
		eventBiteDto5.setName_localized("Film, Media & Entertainment");
		eventBiteDto5.setResourceUri( "https://www.eventbriteapi.com/v3/categories/104/");
		eventBiteDto5.setShort_name("Film & Media");
		eventBiteDto5.setShort_name_localized("Film & Media");

		EventBriteDto eventBiteDto6 = new EventBriteDto();
		eventBiteDto6.setId(110);
		eventBiteDto6.setName("Food & Drink");
		eventBiteDto6.setName_localized("Food & Drink");
		eventBiteDto6.setResourceUri( "https://www.eventbriteapi.com/v3/categories/110/");
		eventBiteDto6.setShort_name("Food & Drink");
		eventBiteDto6.setShort_name_localized("Food & Drink");
		
		EventBriteDto eventBiteDto7 = new EventBriteDto();
		eventBiteDto7.setId(119);
		eventBiteDto7.setName("Hobbies & Special Interest");
		eventBiteDto7.setName_localized("Hobbies & Special Interest");
		eventBiteDto7.setResourceUri( "https://www.eventbriteapi.com/v3/categories/119/");
		eventBiteDto7.setShort_name("Hobbies");
		eventBiteDto7.setShort_name_localized("Hobbies");
		
		EventBriteDto eventBiteDto8 = new EventBriteDto();
		eventBiteDto8.setId(105);
		eventBiteDto8.setName("Performing & Visual Arts");
		eventBiteDto8.setName_localized("Performing & Visual Arts");
		eventBiteDto8.setResourceUri( "https://www.eventbriteapi.com/v3/categories/105/");
		eventBiteDto8.setShort_name("Arts");
		eventBiteDto8.setShort_name_localized("Arts");
		
		EventBriteDto eventBiteDto9 = new EventBriteDto();
		eventBiteDto9.setId(102);
		eventBiteDto9.setName("Science & Technology");
		eventBiteDto9.setName_localized("Science & Technology");
		eventBiteDto9.setResourceUri( "https://www.eventbriteapi.com/v3/categories/102/");
		eventBiteDto9.setShort_name("Science & Tech");
		eventBiteDto9.setShort_name_localized("Science & Tech");
		
		EventBriteDto eventBiteDto10 = new EventBriteDto();
		eventBiteDto10.setId(108);
		eventBiteDto10.setName("Sports & Fitness");
		eventBiteDto10.setName_localized("Sports & Fitness");
		eventBiteDto10.setResourceUri( "https://www.eventbriteapi.com/v3/categories/108/");
		eventBiteDto10.setShort_name("Sports & Fitness");
		eventBiteDto10.setShort_name_localized("Sports & Fitness");
		
		List<EventBriteDto> categoriesList = new ArrayList<>();
		categoriesList.add(eventBiteDto10);
		categoriesList.add(eventBiteDto9);
		categoriesList.add(eventBiteDto8);
		categoriesList.add(eventBiteDto7);
		categoriesList.add(eventBiteDto6);
		categoriesList.add(eventBiteDto5);
		categoriesList.add(eventBiteDto4);
		categoriesList.add(eventBiteDto3);
		categoriesList.add(eventBiteDto2);
		categoriesList.add(eventBiteDto1);
		
		return categoriesList;
	}
	
	@Override
	public Integer getTotalCount() {
		Integer categoryCount = 10;
		return categoryCount;
	}

}
