package com.seeka.app.jobs;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Level;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.service.ICountryService;
import com.seeka.app.service.IFacultyService;
import com.seeka.app.service.ILevelService;

@Component
public class CountryLevelFacultyUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CountryLevelFacultyUtil.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Autowired
	ICountryService countryService;
	
	@Autowired
	ILevelService levelService;
	
	@Autowired
	IFacultyService facultyService;
	
	public static List<CountryDto> countryLevelFacultyList = new ArrayList<CountryDto>();
	public static List<Level> levelMasterList = new ArrayList<Level>();


    @Scheduled(fixedRate = 500000, initialDelay = 5000)
    public void reportCurrentTime() {
    	 log.info("The time is now {}", dateFormat.format(new Date()));
         System.out.println("The time is now {}"+ dateFormat.format(new Date()));
         run();
    }
    
    public static List<CountryDto> getCountryList() {
    	return countryLevelFacultyList;
    }
    
    public static List<Level> getLevelList() {
    	return levelMasterList;
    }
    
    public void run() {
    	System.out.println("CountryLevelFacultyUtil : Job Started: "+new Date());
    	List<CountryDto> countryList = countryService.getAllCountries();
    	List<Level> levelList = levelService.getAllLevelByCountry();
    	Map<BigInteger, List<Level>> levelMap = new HashMap<>(); 
    	for (Level level : levelList) {
    		List<Level> list = levelMap.get(level.getCountryId());
    		if(null != list && !list.isEmpty()) {
    			levelMap.get(level.getCountryId()).add(level);
    			continue;
    		}
    		list = new ArrayList<>();
    		list.add(level);
    		levelMap.put(level.getCountryId(), list);
		}
    	List<Level> levelMasterListTemp = levelService.getAll();
    	levelMasterList.clear();
    	levelMasterList = new ArrayList<Level>(levelMasterListTemp);
    	
    	List<Faculty> facultyList = facultyService.getAllFacultyByCountryIdAndLevel();
    	Map<String, List<Faculty>> facultyMap = new HashMap<>();
    	for (Faculty faculty : facultyList) {
    		String key = faculty.getCountryId()+"-"+faculty.getLevelId();
    		List<Faculty> list = facultyMap.get(key);
    		if(null != list && !list.isEmpty()) {
    			facultyMap.get(key).add(faculty);
    			continue;
    		}
    		list = new ArrayList<>();
    		list.add(faculty);
    		facultyMap.put(key, list);
		}
    	List<CountryDto> finalList = new ArrayList<>();
		int i = 0;
		for (CountryDto countryDto : countryList) {
			i++;
			List<Level> levels = levelMap.get(countryDto.getId());
			if(null != levels && !levels.isEmpty()) {
				for (Level level : levels) {
					String key = countryDto.getId()+"-"+level.getId();
					List<Faculty> list = facultyMap.get(key);
					if(null !=list && !list.isEmpty()) {
						
						level.setFacultyList(list);
					}
				}
				countryDto.setLevelList(levels);
				finalList.add(countryDto);
			}
		}
		countryLevelFacultyList.clear();
		countryLevelFacultyList = new ArrayList<CountryDto>(finalList);
		System.out.println("CountryLevelFacultyUtil : Job Completed: "+new Date());
    }
}