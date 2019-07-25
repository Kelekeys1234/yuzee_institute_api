package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.bean.Hobbies;
import com.seeka.app.dto.UserCountryHobbiesDto;
import com.seeka.app.dto.UserHobbies;
import com.seeka.app.dto.UserInterest;

public interface IHobbyService {
    public void save(Hobbies hobbiesObj);

    public void update(Hobbies hobbiesObj);

    public Hobbies get(BigInteger id);

    public List<Hobbies> searchByHobbies(String hobbyTxt);

    public Map<String, Object> getHobbies();

    public Map<String, Object> addUserHobbies(@Valid UserHobbies userHobbies);

    public Map<String, Object> getUserHobbies(BigInteger userId);

    public Map<String, Object> searchHobbies(String searchText);

    public Map<String, Object> deleteUserHobbies(BigInteger userId, BigInteger hobbyId);

    public Map<String, Object> getInterest();

    public Map<String, Object> searchInterest(String searchText);

    public Map<String, Object> addUserInterest(@Valid UserInterest userInterest);

    public Map<String, Object> getUserInterest(BigInteger userId);

    public Map<String, Object> deleteUserInterest(BigInteger userId, BigInteger interestId);

    public Map<String, Object> addCountryHobbies(@Valid UserCountryHobbiesDto userHountryHobbies);

    public Map<String, Object> getUserHobbiesAndCountry(BigInteger userId);

    public Map<String, Object> deleteInterest(BigInteger userId, BigInteger hobbyId, String countryId);

}
