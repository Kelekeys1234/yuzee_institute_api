package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.bean.Level;
import com.yuzee.app.dao.LevelDao;
import com.yuzee.app.dto.LevelDto;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class LevelProcessor {

    @Autowired
    private LevelDao levelDao;

    public void addUpdateLevel(LevelDto levelDto) {
    	Level level = new Level(null, levelDto.getName(), levelDto.getCode(), null, true, new Date(), null, null, "API", null, null);
    	levelDao.addUpdateLevel(level);
    }

    public Level getLevel(String id) {
        return levelDao.getLevel(id);
    }
    
    public List<LevelDto> getAllLevels() {
    	log.debug("Inside getAllLevels() method");
    	List<LevelDto> levelDtos = new ArrayList<>();
    	log.info("Fetching all levels from DB");
    	List<Level> levelsFromDB = levelDao.getAll();
    	if(!CollectionUtils.isEmpty(levelsFromDB)) {
    		log.info("Levels fetched from DB, start iterating data to make response");
    		levelsFromDB.stream().forEach(level -> {
    			LevelDto levelDto = new LevelDto(level.getId(), level.getName(), level.getCode(), level.getDescription());
    			levelDtos.add(levelDto);
    		});
    	}
        return levelDtos;
    }
    
    public List<Level> getCourseTypeByCountryId(String countryID) {
        return levelDao.getCourseTypeByCountryId(countryID);
    }

    
    public List<Level> getLevelByCountryId(String countryId) {
        return levelDao.getLevelByCountryId(countryId);
    }

    
    public List<Level> getAllLevelByCountry() {
        return levelDao.getAllLevelByCountry();
    }

    
    public Map<String, Object> getCountryLevel(String countryId) {
        Map<String, Object> response = new HashMap<>();
        List<LevelDto> levels = new ArrayList<LevelDto>();
        try {
            levels = levelDao.getCountryLevel(countryId);
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

	
	public List<String> getAllLevelNameByInstituteId(String instituteId) {
		return levelDao.getAllLevelNamesByInstituteId(instituteId);
	}

}
