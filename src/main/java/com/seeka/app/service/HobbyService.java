package com.seeka.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public Hobbies get(String id) {
        return dao.get(id);
    }

    @Override
    public Map<String, Object> getHobbies() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        try {
            hobbies = dao.getAll();
            if (hobbies != null && !hobbies.isEmpty()) {
                response.put("message", "Hobbies fetched successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Hobbies not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", hobbies);
        return response;
    }

    @Override
    public Map<String, Object> addUserHobbies(@Valid UserHobbies userHobbies) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            for (String id : userHobbies.getHobbies()) {
                UserInterestHobbies interestHobbies = new UserInterestHobbies();
                interestHobbies.setHobbies(dao.get(id));
                interestHobbies.setUserInfo(userHobbies.getUserId());
                interestHobbies.setCreatedBy("API");
                interestHobbies.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
                interestHobbies.setUpdatedBy("API");
                interestHobbies.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                userHobbyDao.save(interestHobbies);
                response.put("message", "Hobbies fetched successfully");
                response.put("status", HttpStatus.OK.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> getUserHobbies(String userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        try {
            hobbies = userHobbyDao.getUserHobbies(userId);
            if (hobbies != null && !hobbies.isEmpty()) {
                response.put("message", "Hobbies fetched successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Hobbies not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", hobbies);
        return response;
    }

    @Override
    public Map<String, Object> searchHobbies(String searchText) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        try {
            hobbies = dao.searchHobbies(searchText);
            if (hobbies != null && !hobbies.isEmpty()) {
                response.put("message", "Hobbies fetched successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Hobbies not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", hobbies);
        return response;
    }

    @Override
    public Map<String, Object> deleteUserHobbies(String userId, String hobbyId) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            userHobbyDao.deleteUserHobbies(userId, hobbyId);
            response.put("message", "Hobbies deleted successfully");
            response.put("status", HttpStatus.OK.value());
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> getInterest() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Interest> interest = new ArrayList<Interest>();
        try {
            interest = interestDao.getAll();
            if (interest != null && !interest.isEmpty()) {
                response.put("message", "Interest fetched successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Interest not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", interest);
        return response;
    }

    @Override
    public Map<String, Object> searchInterest(String searchText) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Interest> interest = new ArrayList<Interest>();
        try {
            interest = interestDao.searchInterest(searchText);
            if (interest != null && !interest.isEmpty()) {
                response.put("message", "Interest fetched successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Interest not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", interest);
        return response;
    }

    @Override
    public Map<String, Object> addUserInterest(@Valid UserInterest userInterest) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            for (String id : userInterest.getInterest()) {
                com.seeka.app.bean.UserInterest interestHobbies = new com.seeka.app.bean.UserInterest();
                interestHobbies.setInterest(interestDao.get(id));
                interestHobbies.setUserInfo(userInterest.getUserId());
                interestHobbies.setCreatedBy("API");
                interestHobbies.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
                interestHobbies.setUpdatedBy("API");
                interestHobbies.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                userInterestDao.save(interestHobbies);
                response.put("message", "Interest added successfully");
                response.put("status", HttpStatus.OK.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> getUserInterest(String userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Interest> interests = new ArrayList<Interest>();
        try {
            interests = userInterestDao.getUserInterest(userId);
            if (interests != null && !interests.isEmpty()) {
                response.put("message", "Interest fetched successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Interest not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", interests);
        return response;
    }

    @Override
    public Map<String, Object> deleteUserInterest(String userId, String interestId) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            userInterestDao.deleteUserInterest(userId, interestId);
            response.put("message", "Interest deleted successfully");
            response.put("status", HttpStatus.OK.value());
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> addCountryHobbies(@Valid UserCountryHobbiesDto userHountryHobbies) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            if (userHountryHobbies.getHobbies() != null && !userHountryHobbies.getHobbies().isEmpty()) {
                userHobbyDao.deleteUserAllHobbies(userHountryHobbies.getUserId());
            }
            if (userHountryHobbies.getCountry() != null && !userHountryHobbies.getCountry().isEmpty()) {
                userHobbyDao.deleteUserAllCountries(userHountryHobbies.getUserId());
            }
            for (String id : userHountryHobbies.getHobbies()) {
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
            response.put("message", "Interest added successfully");
            response.put("status", HttpStatus.OK.value());
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> getUserHobbiesAndCountry(String userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        List<String> countries = new ArrayList<String>();
        try {
            hobbies = userHobbyDao.getUserHobbies(userId);
            countries = userHobbyDao.getCountryByUserId(userId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        response.put("message", "Interest fatched successfully");
        response.put("status", HttpStatus.OK.value());
        response.put("hobbies", hobbies);
        response.put("countries", countries);
        return response;
    }

    @Override
    public Map<String, Object> deleteInterest(String userId, String hobbyId, String countryId) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            if (hobbyId != null) {
                userHobbyDao.deleteUserHobbies(userId, hobbyId);
            }
            if (countryId != null && !countryId.isEmpty()) {
                userHobbyDao.deleteUserCountry(userId, countryId);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        response.put("message", "Interest deleted successfully");
        response.put("status", HttpStatus.OK.value());
        return response;
    }

    @Override
    public Map<String, Object> autoSearch(String searchKey) {
        Map<String, Object> response = new HashMap<>();
        List<Hobbies> hobbies = new ArrayList<Hobbies>();
        try {
            hobbies = dao.autoSearch(1, 50, searchKey);
            if (hobbies != null && !hobbies.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Hobby fetched successfully");
                response.put("courses", hobbies);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Hobby not found");
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

	@Override
	public Interest getInterest(String id) {
		return interestDao.get(id);
	}
}
