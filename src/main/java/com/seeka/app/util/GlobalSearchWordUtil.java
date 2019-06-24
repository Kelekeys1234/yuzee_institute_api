package com.seeka.app.util;

import java.util.ArrayList;
import java.util.List;

public class GlobalSearchWordUtil {
	
	public static String[] toBeIgnoredWords = {"the", "in", "on", "of", "or","i"};
	public static List<String> countries = new ArrayList<>();
	public static List<String> cities = new ArrayList<>();
	
	public static void updateCountries(List<String> countryNameList) {
		if(null != countryNameList && !countryNameList.isEmpty()) {
			countries.clear();
			countries.addAll(countryNameList);
		}
	}
	
	public static void updateCities(List<String> cityNameList) {
		if(null != cityNameList && !cityNameList.isEmpty()) {
			cities.clear();
			cities.addAll(cityNameList);
		}
	}
	
	public static List<String> getCountries() {
		return countries;
	}
	
	public static List<String> getCities() {
		return cities;
	}
	 
	public static Boolean isValidCountryName(String name) {
		return countries.contains(name);
	}
	
	public static Boolean isValidCityName(String name) {
		return cities.contains(name);
	}
	
	public static Boolean isToBeRemovedWord(String name) {
		for (String word : toBeIgnoredWords) {
			if(word.toLowerCase().equals(name.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

}
