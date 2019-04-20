package com.seeka.app.jobs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.City;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.service.ICityService;
import com.seeka.app.service.ICountryService;

@Component
public class CountryUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CountryUtil.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Autowired
	ICountryService countryService;
	
	@Autowired
	ICityService cityService;
	
	public static List<CountryDto> countryList = new ArrayList<CountryDto>();

    @Scheduled(fixedRate = 500000, initialDelay = 5000)
    public void reportCurrentTime() {
    	 log.info("The time is now {}", dateFormat.format(new Date()));
         System.out.println("The time is now {}"+ dateFormat.format(new Date()));
         run();
    }
    
    public static List<CountryDto> getCountryList() {
    	return countryList;
    }
    
    public void run() {
    	System.out.println("CountryUtil : Job Started: "+new Date());
    	List<CountryDto> countryListTemp = countryService.getAllCountries();
    	List<City> cityList = cityService.getAll();
    	Map<UUID, List<City>> cityMap = new HashMap<>(); 
    	for (City city : cityList) {
    		List<City> list = cityMap.get(city.getCountryObj().getId());
    		if(null != list && !list.isEmpty()) {
    			cityMap.get(city.getCountryObj().getId()).add(city);
    			continue;
    		}
    		list = new ArrayList<>();
    		list.add(city);
    		cityMap.put(city.getCountryObj().getId(), list);
		}
    	
    	List<CountryDto> finalList = new ArrayList<>();
		for (CountryDto countryDto : countryListTemp) {
			List<City> list = cityMap.get(countryDto.getId());
			if(null != list && !list.isEmpty()) {
				list.sort((City s1, City s2)->s1.getName().compareTo(s2.getName()));
				countryDto.setCityList(list);
				finalList.add(countryDto);
			}
		}
		
		finalList.sort((CountryDto s1, CountryDto s2)->s1.getName().compareTo(s2.getName()));
		countryList.clear();
		countryList = new ArrayList<CountryDto>(finalList);
		System.out.println("countryList : "+countryList.size());
		System.out.println("CountryUtil : Job Completed: "+new Date());
    }
    
    
}
