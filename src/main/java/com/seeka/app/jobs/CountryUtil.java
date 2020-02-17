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

    public static List<CountryDto> univCountryList = new ArrayList<CountryDto>();
    public static List<CountryDto> countryList = new ArrayList<CountryDto>();
    public static Map<String, List<City>> countryCityMap = new HashMap<>();
    public static Map<String, CountryDto> countryMap = new HashMap<>();

    @Scheduled(fixedRate = 500000, initialDelay = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        System.out.println("The time is now {}" + dateFormat.format(new Date()));
        run();
    }

    public static List<CountryDto> getCountryList() {
        return countryList;
    }

    public static List<CountryDto> getUnivCountryList() {
        return univCountryList;
    }

    public static List<City> getCityListByCountryId(String countryId) {
        return countryCityMap.get(countryId);
    }

    public static CountryDto getCountryByCountryId(String countryId) {
        return countryMap.get(countryId);
    }

    public void run() {
        System.out.println("CountryUtil : Job Started: " + new Date());
        List<CountryDto> countryListTemp = countryService.getAllCountries();

        List<CountryDto> countriesWithUniversity = countryService.getAllUniversityCountries();

        List<City> cityList = cityService.getAll();
        Map<String, List<City>> cityMapTemp = new HashMap<>();
        for (City city : cityList) {
            if (city.getCountry() != null) {
                List<City> list = cityMapTemp.get(city.getCountry().getId());
                if (null != list && !list.isEmpty()) {
                    cityMapTemp.get(city.getCountry().getId()).add(city);
                    continue;
                }
                list = new ArrayList<>();
                list.add(city);
                cityMapTemp.put(city.getCountry().getId(), list);
            }
        }

        List<CountryDto> finalList = new ArrayList<>();
        for (CountryDto countryDto : countryListTemp) {
            List<City> list = cityMapTemp.get(countryDto.getId());
            if (null != list && !list.isEmpty()) {
                list.sort((City s1, City s2) -> s1.getName().compareTo(s2.getName()));
                countryDto.setCityList(list);
                finalList.add(countryDto);
            }
            countryMap.put(countryDto.getId(), countryDto);
        }

        List<CountryDto> finalList1 = new ArrayList<>();
        for (CountryDto countryDto : countriesWithUniversity) {
            List<City> list = cityMapTemp.get(countryDto.getId());
            if (null != list && !list.isEmpty()) {
                list.sort((City s1, City s2) -> s1.getName().compareTo(s2.getName()));
                countryDto.setCityList(list);
                finalList1.add(countryDto);
            }
        }

        countryCityMap.clear();
        countryCityMap.putAll(cityMapTemp);

        finalList1.sort((CountryDto s1, CountryDto s2) -> s1.getName().compareTo(s2.getName()));

        finalList.sort((CountryDto s1, CountryDto s2) -> s1.getName().compareTo(s2.getName()));
        countryList.clear();
        countryList = new ArrayList<CountryDto>(finalList);

        univCountryList.clear();
        univCountryList = new ArrayList<CountryDto>(finalList1);

        System.out.println("countryList : " + countryList.size());
        System.out.println("univCountryList : " + univCountryList.size());
        System.out.println("CountryUtil : Job Completed: " + new Date());
    }

}
