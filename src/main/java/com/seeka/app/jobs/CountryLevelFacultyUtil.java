package com.seeka.app.jobs;

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


    @Scheduled(fixedRate = 500000, initialDelay = 5000)
    public void reportCurrentTime() {
    	 log.info("The time is now {}", dateFormat.format(new Date()));
         System.out.println("The time is now {}"+ dateFormat.format(new Date()));
         run();
    }
    
    public static List<CountryDto> getCountryList() {
    	return countryLevelFacultyList;
    }
    
    public void run() {
    	System.out.println("Job Started: "+new Date());
    	List<CountryDto> countryList = countryService.getAllCountries();
    	Integer countrySize = countryList.size();
    	System.out.println("No Of countries: "+countrySize);
    	
    	List<Level> levelList = levelService.getAllLevelByCountry();
    	
    	System.out.println("No Of levels: "+levelList.size());
    	
    	Map<Integer, List<Level>> levelMap = new HashMap<>(); 
    	
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
    	System.out.println("Level Grouping done at "+new Date()+", Size:"+levelMap.size());
    	
    	
    	List<Faculty> facultyList = facultyService.getAllFacultyByCountryIdAndLevel();
    	
    	System.out.println("No Of faculties: "+facultyList.size());
    	
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
    	
    	System.out.println("Faculty Grouping done at "+new Date()+", Size:"+facultyMap.size());
    	
		List<CountryDto> finalList = new ArrayList<>();
		
		int i = 0;
		
		for (CountryDto countryDto : countryList) {
			i++;
			List<Level> levels = levelMap.get(countryDto.getId());
			if(null != levels && !levels.isEmpty()) {
				System.out.println("Total Country: "+countrySize+", Current: "+i+", Levels : "+levelList.size());
				for (Level level : levelList) {
					String key = countryDto.getId()+"-"+level.getId();
					List<Faculty> list = facultyMap.get(key);
					if(null !=list && !list.isEmpty()) {
						System.out.println("KEY: "+key+", SMapSize: "+list.size());
						level.setFacultyList(list);
					}
				}
				countryDto.setLevelList(levelList);;
				finalList.add(countryDto);
			}
		}
		countryLevelFacultyList.clear();
		countryLevelFacultyList = new ArrayList<CountryDto>(finalList);
		System.out.println("Job Completed: "+new Date());
    }
    
    
}
