package com.seeka.app.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.bean.Hobbies;
import com.seeka.app.bean.Interest;
import com.seeka.app.dto.UserCountryHobbiesDto;
import com.seeka.app.dto.UserHobbies;
import com.seeka.app.dto.UserInterest;

public interface IHobbyService {
    public void save(Hobbies hobbiesObj);

    public void update(Hobbies hobbiesObj);

    public Hobbies get(String id);
    
    public Interest getInterest(String id);

    public List<Hobbies> searchByHobbies(String hobbyTxt);

    public Map<String, Object> getHobbies();

    public Map<String, Object> addUserHobbies(@Valid UserHobbies userHobbies);

    public Map<String, Object> getUserHobbies(String userId);

    public Map<String, Object> searchHobbies(String searchText);

    public Map<String, Object> deleteUserHobbies(String userId, String hobbyId);

    public Map<String, Object> getInterest();

    public Map<String, Object> searchInterest(String searchText);

    public Map<String, Object> addUserInterest(@Valid UserInterest userInterest);

    public Map<String, Object> getUserInterest(String userId);

    public Map<String, Object> deleteUserInterest(String userId, String interestId);

    public Map<String, Object> addCountryHobbies(@Valid UserCountryHobbiesDto userHountryHobbies);

    public Map<String, Object> getUserHobbiesAndCountry(String userId);

    public Map<String, Object> deleteInterest(String userId, String hobbyId, String countryId);

    public Map<String, Object> autoSearch(String searchKey);

}
