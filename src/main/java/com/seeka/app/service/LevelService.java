package com.seeka.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Level;
import com.seeka.app.dao.ILevelDAO;

@Service
@Transactional
public class LevelService implements ILevelService {

    @Autowired
    private ILevelDAO dao;

    @Override
    public void save(Level obj) {
        dao.save(obj);
    }

    @Override
    public void update(Level obj) {
        dao.update(obj);
    }

    @Override
    public Level get(String id) {
        return dao.get(id);
    }

    @Override
    public List<Level> getAll() {
        return dao.getAll();
    }

    @Override
    public List<Level> getCourseTypeByCountryId(String countryID) {
        return dao.getCourseTypeByCountryId(countryID);
    }

    @Override
    public List<Level> getLevelByCountryId(String countryId) {
        return dao.getLevelByCountryId(countryId);
    }

    @Override
    public List<Level> getAllLevelByCountry() {
        return dao.getAllLevelByCountry();
    }

    @Override
    public Map<String, Object> getCountryLevel(String countryId) {
        Map<String, Object> response = new HashMap<>();
        List<Level> levels = new ArrayList<Level>();
        try {
            levels = dao.getCountryLevel(countryId);
            if (levels != null && !levels.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Level fetched successfully");
                response.put("courses", levels);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Level not found");
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

	@Override
	public List<String> getAllLevelNameByInstituteId(String instituteId) {
		return dao.getAllLevelNamesByInstituteId(instituteId);
	}
}
