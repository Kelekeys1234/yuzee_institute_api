package com.yuzee.app.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;

import com.yuzee.app.bean.Level;

public interface LevelDao {

    public void addUpdateLevel(Level level);

    public Optional<Level> getLevel(UUID levelId);

    public List<Level> getAll();
    
	public List<Level> findByIdIn(List<UUID> ids);
	
	public Level getLevelByLevelCode(String levelCode);
	
	@Cacheable(value = "cacheLevelMap", unless = "#result == null")
	public Map<String, UUID> getAllLevelMap();

}
