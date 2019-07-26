package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Hobbies;
import com.seeka.app.bean.Interest;
import com.seeka.app.bean.UserBiginterestCountry;
import com.seeka.app.bean.UserInterestHobbies;
import com.seeka.app.dao.IHobbyDAO;
import com.seeka.app.dao.InterestDao;
import com.seeka.app.dao.UserHobbyDao;
import com.seeka.app.dao.UserInterestDao;
import com.seeka.app.dto.UserCountryHobbiesDto;
import com.seeka.app.dto.UserHobbies;
import com.seeka.app.dto.UserInterest;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class HobbyService implements IHobbyService {

    @Autowired
    private IHobbyDAO dao;

    @Autowired
    private UserHobbyDao userHobbyDao;

    @Autowired
    private InterestDao interestDao;

    @Autowired
    private UserInterestDao userInterestDao;

    @Override
    public void save(Hobbies hobbiesObj) {
        dao.save(hobbiesObj);
    }

    @Override
    public void update(Hobbies hobbiesObj) {
        dao.update(hobbiesObj);
    }

    @Override
    public List<Hobbies> searchByHobbies(String hobbyTxt) {
        return dao.searchByHobbies(hobbyTxt);
    }

    @Override
    public Hobbies get(BigInteger id) {
        return dao.get(id);
    }

    @Override
    public Map<String, Object> getHobbies() {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        List<Hobbies> courses = new ArrayList<Hobbies>();
        try {
            courses = dao.getAll();
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        response.put("hobbies", courses);
        return response;
    }

    @Override
    public Map<String, Object> addUserHobbies(@Valid UserHobbies userHobbies) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        try {
            for (BigInteger id : userHobbies.getHobbies()) {
                UserInterestHobbies interestHobbies = new UserInterestHobbies();
                interestHobbies.setHobbies(dao.get(id));
                interestHobbies.setUserInfo(userHobbies.getUserId());
                interestHobbies.setCreatedBy("API");
                interestHobbies.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
                interestHobbies.setUpdatedBy("API");
                interestHobbies.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                userHobbyDao.save(interestHobbies);
            }
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }

    @Override
    public Map<String, Object> getUserHobbies(BigInteger userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        try {
            hobbies = userHobbyDao.getUserHobbies(userId);
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        response.put("hobbies", hobbies);
        return response;
    }

    @Override
    public Map<String, Object> searchHobbies(String searchText) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        try {
            hobbies = dao.searchHobbies(searchText);
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        response.put("hobbies", hobbies);
        return response;
    }

    @Override
    public Map<String, Object> deleteUserHobbies(BigInteger userId, BigInteger hobbyId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        try {
            userHobbyDao.deleteUserHobbies(userId, hobbyId);
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }

    @Override
    public Map<String, Object> getInterest() {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        List<Interest> interest = new ArrayList<Interest>();
        try {
            interest = interestDao.getAll();
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        response.put("interest", interest);
        return response;
    }

    @Override
    public Map<String, Object> searchInterest(String searchText) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        List<Interest> interest = new ArrayList<Interest>();
        try {
            interest = interestDao.searchInterest(searchText);
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        response.put("interests", interest);
        return response;
    }

    @Override
    public Map<String, Object> addUserInterest(@Valid UserInterest userInterest) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        try {
            for (BigInteger id : userInterest.getInterest()) {
                com.seeka.app.bean.UserInterest interestHobbies = new com.seeka.app.bean.UserInterest();
                interestHobbies.setInterest(interestDao.get(id));
                interestHobbies.setUserInfo(userInterest.getUserId());
                interestHobbies.setCreatedBy("API");
                interestHobbies.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
                interestHobbies.setUpdatedBy("API");
                interestHobbies.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                userInterestDao.save(interestHobbies);
            }
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }

    @Override
    public Map<String, Object> getUserInterest(BigInteger userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        List<Interest> interests = new ArrayList<Interest>();
        try {
            interests = userInterestDao.getUserInterest(userId);
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        response.put("interests", interests);
        return response;
    }

    @Override
    public Map<String, Object> deleteUserInterest(BigInteger userId, BigInteger interestId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        try {
            userInterestDao.deleteUserInterest(userId, interestId);
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }

    @Override
    public Map<String, Object> addCountryHobbies(@Valid UserCountryHobbiesDto userHountryHobbies) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        try {
            if (userHountryHobbies.getHobbies() != null && !userHountryHobbies.getHobbies().isEmpty()) {
                userHobbyDao.deleteUserAllHobbies(userHountryHobbies.getUserId());
            }
            if (userHountryHobbies.getCountry() != null && !userHountryHobbies.getCountry().isEmpty()) {
                userHobbyDao.deleteUserAllCountries(userHountryHobbies.getUserId());
            }
            for (BigInteger id : userHountryHobbies.getHobbies()) {
                UserInterestHobbies interestHobbies = new UserInterestHobbies();
                interestHobbies.setHobbies(dao.get(id));
                interestHobbies.setUserInfo(userHountryHobbies.getUserId());
                interestHobbies.setCreatedBy("API");
                interestHobbies.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
                interestHobbies.setUpdatedBy("API");
                interestHobbies.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                userHobbyDao.save(interestHobbies);
            }
            for (String name : userHountryHobbies.getCountry()) {
                UserBiginterestCountry userBiginterestCountry = new UserBiginterestCountry();
                userBiginterestCountry.setUserInfo(userHountryHobbies.getUserId());
                userBiginterestCountry.setCountry(name);
                userBiginterestCountry.setCreatedBy("API");
                userBiginterestCountry.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
                userBiginterestCountry.setUpdatedBy("API");
                userBiginterestCountry.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                userHobbyDao.saveUserCountry(userBiginterestCountry);
            }
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }

    @Override
    public Map<String, Object> getUserHobbiesAndCountry(BigInteger userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        List<String> countries = new ArrayList<String>();
        try {
            hobbies = userHobbyDao.getUserHobbies(userId);
            countries = userHobbyDao.getCountryByUserId(userId);
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        response.put("hobbies", hobbies);
        response.put("countries", countries);
        return response;
    }

    @Override
    public Map<String, Object> deleteInterest(BigInteger userId, BigInteger hobbyId, String countryId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        try {
            if (hobbyId != null) {
                userHobbyDao.deleteUserHobbies(userId, hobbyId);
            }
            if (countryId != null && !countryId.isEmpty()) {
                userHobbyDao.deleteUserCountry(userId, countryId);
            }
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }
}
